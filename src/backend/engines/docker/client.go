package main

import (
	"context"
	"github.com/docker/docker/client"
)

type Client struct {
	cli *client.Client
	ctx context.Context
}

func NewClient() *Client {
	cli, err := client.NewClientWithOpts(client.FromEnv)
	if err != nil {
		panic(err)
	}
	client := Client{
		cli,
		context.Background(),
	}
	return &client
}
