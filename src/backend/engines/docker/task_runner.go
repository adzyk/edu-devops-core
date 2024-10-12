package main

import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"github.com/docker/docker/api/types"
	controlapi "github.com/moby/buildkit/api/services/control"
	"log"
	"strings"
	"time"
)

func TaskRun(task *Task) {
	client := NewClient()

	imageId := fmt.Sprintf("%v-%s:latest", task.JobId, time.Now().Format("20060102030405"))
	result, err := client.Build(BuildRequest{
		DockerfileName: "Dockerfile",
		ImageName:      imageId,
		BuilderVersion: getBuildVersion(task.EngineType),
		dockerfile:     task.RequestData["main"],
		contextPath:    task.RequestData["context_path"],
	})
	if err != nil {
		KafkaCurrentClient.Produce(TaskResultBody(task.JobId, false, err.Error(), nil))
		return
	}

	errorMessages := make([]string, 0)
	logMessage := make([]string, 0)
	lastError := ""

	for _, line := range strings.Split(*result, "\n") {
		if strings.HasPrefix(line, "{") {
			ln, err := parseLog(line, getBuildVersion(task.EngineType))
			if err != nil {
				errorMessages = append(errorMessages, fmt.Sprintf("ERR: %s\n", err.Error()))
				lastError = err.Error()
			}
			if ln != nil {
				logMessage = append(logMessage, fmt.Sprintf("%s\n", *ln))
			}
		}
	}

	if len(errorMessages) > 0 {
		KafkaCurrentClient.Produce(TaskResultBody(task.JobId, false, lastError, errorMessages))
		return
	}

	logMessage = append(logMessage, "\n\nRun Tests:\n")
	finalStatus := true
	for _, testCase := range task.TestCases {
		status, err := client.TestRun(imageId, testCase)
		if !status {
			finalStatus = false
		}
		logMessage = append(logMessage, fmt.Sprintf("CaseId: %v, Result: %v, Err: %v", testCase.CaseId, status, err))
	}
	KafkaCurrentClient.Produce(TaskResultBody(task.JobId, finalStatus, "", logMessage))
}

func TaskResultBody(jobId int64, status bool, errorMsg string, logMessages []string) []byte {
	res := TaskResult{
		status,
		logMessages,
		errorMsg,
		jobId,
	}

	body, err := json.Marshal(res)
	if err != nil {
		log.Printf("ERR: %s\n", err.Error())
	}
	return body
}

func getBuildVersion(buildVersion string) types.BuilderVersion {
	if buildVersion == "docker" {
		return types.BuilderV1
	}
	if buildVersion == "docker_buildx" {
		return types.BuilderBuildKit
	}
	return types.BuilderV1
}

func parseLog(line string, version types.BuilderVersion) (*string, error) {
	if version == types.BuilderV1 {
		newLine := LogLine{}
		err := json.Unmarshal([]byte(line), &newLine)
		if err != nil {
			return nil, err
		} else {
			if newLine.Error != nil {
				return nil, fmt.Errorf(*newLine.Error)
			}
			return &newLine.Stream, nil
		}
	}
	if version == types.BuilderBuildKit {
		// TODO использоваться парсер из кода moby
		newLine := LogLine2{}
		err := json.Unmarshal([]byte(line), &newLine)
		if err != nil {
			return nil, err
		}

		if newLine.Id == "moby.buildkit.trace" {
			data, err := base64.StdEncoding.DecodeString(newLine.Aux.(string))
			aux := controlapi.StatusResponse{}
			err = aux.Unmarshal(data)
			if err != nil {
				return nil, err
			} else {
				listDigest := make([]string, 0)
				message := ""
				for _, ln := range aux.Vertexes {
					if ln.Completed != nil {
						repeat := false
						for _, dg := range listDigest {
							if ln.Digest.Hex() == dg {
								repeat = true
							}
						}
						if repeat {
							continue
						}
						listDigest = append(listDigest, ln.Digest.Hex())
						message = message + "\n" + ln.Name
					}
				}
				return &message, nil
			}
		}
	}
	return nil, nil
}
