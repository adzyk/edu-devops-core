package main

import (
	"encoding/json"
	"fmt"
	"github.com/docker/docker/api/types/container"
	"io"
	"log"
	"net/http"
	"strconv"
	"strings"
	"time"

	jsonpath "github.com/PaesslerAG/jsonpath"
)

func (c *Client) TestRun(ID string, testcase TestCase) (bool, error) {
	switch testcase.CaseType {
	case "request":
		return c.curlCase(ID, testcase)
	case "context":
		return c.contextCase(ID, testcase)
	default:
		return false, fmt.Errorf("not support case type")
	}
}

func (c *Client) curlCase(ID string, testcase TestCase) (bool, error) {

	id, err := c.StartContainer(StartRequest{ImageName: ID})
	if err != nil {
		return false, err
	}
	defer c.stopContainer(id)

	//TODO wait container ???
	time.Sleep(3 * time.Second)

	info, err := c.cli.ContainerInspect(c.ctx, *id)
	if err != nil {
		return false, err
	}
	log.Printf("Get info container %s, IP:%s\n", info.Name, info.NetworkSettings.IPAddress)
	var IPAddress = info.NetworkSettings.IPAddress

	contextCase := testcase.getContextRequest()

	body := strings.NewReader(contextCase.RequestBody)
	request, err := http.NewRequest(contextCase.TypeRequest, fmt.Sprintf("http://%s:%d%s",
		IPAddress, contextCase.ContainerPort, contextCase.UrlPath), body)
	if err != nil {
		return false, err
	}
	resp, err := http.DefaultClient.Do(request)
	if err != nil {
		return false, err
	}

	if resp.StatusCode != contextCase.ExpectedCode {
		return false, fmt.Errorf("Response CODE does not match\nResult: %d\nExpected: %d",
			resp.StatusCode, contextCase.ExpectedCode)
	}

	if contextCase.ExpectedBody != "" {
		body, err := io.ReadAll(resp.Body)
		if err != nil {
			return false, err
		}
		strBody := string(body)
		if strBody != contextCase.ExpectedBody {
			return false, fmt.Errorf("Response body does not match\nResult: %s\nExpected: %s",
				strBody, contextCase.ExpectedBody)
		}
	}

	return true, nil
}

func (c *Client) stopContainer(ID *string) {
	_ = c.cli.ContainerStop(c.ctx, *ID, container.StopOptions{})
	_ = c.cli.ContainerRemove(c.ctx, *ID, container.RemoveOptions{})
}

func (c *Client) contextCase(ID string, testcase TestCase) (bool, error) {
	inspect, err := c.ImageContent(ID)
	if err != nil {
		return false, err
	}
	contextTest := testcase.getContextContext()

	body, err := json.Marshal(inspect)
	if err != nil {
		return false, err
	}
	bodyObject := interface{}(nil)
	err = json.Unmarshal(body, &bodyObject)
	if err != nil {
		return false, err
	}
	result, err := jsonpath.Get(contextTest.Field, bodyObject)
	if err != nil {
		return false, err
	}

	switch contextTest.Operator {
	case "<":
		res := lessInt(result.(float64), contextTest.Value)
		var err error
		if !res {
			err = fmt.Errorf("does not satisfy the condition %v < %s", result, contextTest.Value)
		}
		return res, err
	case "=":
		resultStr := resultByString(result)
		res := contextTest.Value == resultStr
		var err error
		if !res {
			err = fmt.Errorf("expected: %s, result: %s", contextTest.Value, resultStr)
		}
		return res, err
	}
	return false, nil
}

func lessInt(intA float64, intB string) bool {
	intBVal, _ := strconv.ParseFloat(intB, 64)
	if intA < intBVal {
		return true
	}
	return false
}

func resultByString(result interface{}) string {
	if res, ok := result.(string); ok {
		return res
	}
	if res, ok := result.(map[string]interface{}); ok {
		newStr := ""
		for k, v := range res {
			newStr = newStr + k + "=" + fmt.Sprintf("%v", v)
		}
		return newStr
	}
	return ""
}
