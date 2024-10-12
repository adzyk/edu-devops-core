package main

import (
	"github.com/docker/docker/api/types/container"
	"github.com/docker/docker/api/types/network"
	"log"
)

type StartRequest struct {
	ImageName string
}

func (c *Client) StartContainer(startRequest StartRequest) (*string, error) {

	containerResponse, err := c.cli.ContainerCreate(
		c.ctx,
		&container.Config{
			Image: startRequest.ImageName,
			Tty:   false},
		&container.HostConfig{},
		&network.NetworkingConfig{},
		nil,
		"")

	if err != nil {
		return nil, err
	}

	log.Printf("Start container ID: %s\n", containerResponse.ID)

	err = c.cli.ContainerStart(c.ctx, containerResponse.ID, container.StartOptions{})
	if err != nil {
		log.Printf("ERROR: %s\n", err.Error())
	}
	return &containerResponse.ID, nil
}
