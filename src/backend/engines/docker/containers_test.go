package main

import (
	"github.com/docker/docker/api/types/container"
	"testing"
	"time"
)

func TestStartContainer(t *testing.T) {
	client := NewClient()

	id, err := client.StartContainer(StartRequest{ImageName: "flask-app:latest"})
	if err != nil {
		t.Error(err)
	}
	t.Log(id)

	//TODO wait container ???
	time.Sleep(3 * time.Second)

	caseTest := TestCase{
		CaseId:   1,
		CaseType: "request",
		Context: ContextRequest{
			5000,
			"/hello",
			"",
			"GET",
			200,
			"Hi, Bro!",
		},
	}
	status, err := client.TestRun(*id, caseTest)
	if !status {
		t.Error(err)
	}

	err = client.cli.ContainerStop(client.ctx, *id, container.StopOptions{})
	if err != nil {
		t.Error(err)
	}
	err = client.cli.ContainerRemove(client.ctx, *id, container.RemoveOptions{})
	if err != nil {
		t.Error(err)
	}
}

func TestContextContainer(t *testing.T) {
	client := NewClient()

	time.Sleep(3 * time.Second)

	caseTest := TestCase{
		CaseId:   2,
		CaseType: "context",
		Context: ContextTest{
			"$.Size",
			"<",
			"60000000",
		},
	}
	status, err := client.TestRun("flask-app:latest", caseTest)
	t.Log("status", status, err)
	if !status {
		t.Error(err)
	}
}
