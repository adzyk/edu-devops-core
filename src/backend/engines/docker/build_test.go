package main

import (
	"encoding/json"
	"fmt"
	"github.com/docker/docker/api/types"
	"log"
	"strings"
	"testing"
	"time"
)

const (
	df = `
FROM python:3.8-alpine
COPY ./requirements.txt /app/requirements.txt
WORKDIR /app
RUN pip install -r requirements.txt
COPY . /app
ENTRYPOINT [ "python" ]

CMD ["main.py" ]
`
)

func TestBuild(t *testing.T) {
	client := NewClient()

	result, err := client.Build(
		BuildRequest{
			DockerfileName: "Dockerfile",
			ImageName:      fmt.Sprintf("image-%v-%s:latest", 1, time.Now().Format("20060102030405")),
			BuilderVersion: types.BuilderV1,
			dockerfile:     df,
			contextPath:    "./contexts/case1/",
		})
	if err != nil {
		t.Error(err)
	}
	for _, line := range strings.Split(*result, "\n") {
		if strings.HasPrefix(line, "{") {
			newLine := LogLine{}
			err := json.Unmarshal([]byte(line), &newLine)
			if err != nil {
				log.Printf("ERR: %s\n", err.Error())
				log.Printf("Line: \n%s\n", line)
			}
			if newLine.Error != nil {
				t.Errorf("BUILD ERR: %s\n", *newLine.Error)
			}
		}
	}
}

func TestName(t *testing.T) {
	name := fmt.Sprintf("image-%v-%s:latest", 1, time.Now().Format("20060102030405"))
	t.Log(name)
	t.Fail()
}
