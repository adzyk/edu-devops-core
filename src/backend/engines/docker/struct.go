package main

import (
	"encoding/json"
)

type Task struct {
	JobId       int64             `json:"job_id"`
	EngineType  string            `json:"engine_type"`
	RequestData map[string]string `json:"request_data"`
	TestCases   []TestCase        `json:"test_cases"`
}

type TaskResult struct {
	Status bool     `json:"status"`
	Log    []string `json:"log"`
	Result string   `json:"result"`
	JobId  int64    `json:"job_id"`
}

type TestCase struct {
	CaseId   int64       `json:"case_id"`
	CaseType string      `json:"case_type"`
	Context  interface{} `json:"context"`
}

func (c *TestCase) getContextRequest() ContextRequest {
	requestBytes, _ := json.Marshal(c.Context)
	contextRequest := ContextRequest{}
	json.Unmarshal(requestBytes, &contextRequest)
	return contextRequest
}

func (c *TestCase) getContextContext() ContextTest {
	caseBytes, _ := json.Marshal(c.Context)
	caseTest := ContextTest{}
	json.Unmarshal(caseBytes, &caseTest)
	return caseTest
}

type ContextRequest struct {
	ContainerPort int    `json:"container_port"`
	UrlPath       string `json:"url_path"`
	RequestBody   string `json:"request_body"`
	TypeRequest   string `json:"type_request"`
	ExpectedCode  int    `json:"expected_code"`
	ExpectedBody  string `json:"expected_body"`
}

type ContextTest struct {
	Field    string `json:"field"`
	Operator string `json:"operator"`
	Value    string `json:"value"`
}

type LogLine struct {
	Stream      string `json:"stream,omitempty"`
	Status      string `json:"status,omitempty"`
	*Aux        `json:"aux,omitempty"`
	Error       *string       `json:"error,omitempty"`
	ErrorDetail *ErrorMessage `json:"errorMessage,omitempty"`
}

type Aux struct {
	ID string
}

type ErrorMessage struct {
	Message string `json:"message,omitempty"`
}

type LogLine2 struct {
	Id  string      `json:"id"`
	Aux interface{} `json:"aux"`
}
