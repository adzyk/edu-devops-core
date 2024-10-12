package main

import "testing"

func TestTestCaseContextCast(t *testing.T) {

	var c interface{} = map[string]interface{}{
		"field":    "$.xxx",
		"operator": "<",
		"value":    "Test",
	}

	test := TestCase{
		1,
		"docker",
		c}

	res := test.getContextContext()
	if res.Value != "Test" {
		t.Fail()
	}
}
