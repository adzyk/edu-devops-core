package main

import (
	"github.com/docker/docker/api/types"
	"log"
)

func (c *Client) CreateNetwork() {
	networkResponse, err := c.cli.NetworkCreate(c.ctx, "test-network", types.NetworkCreate{Driver: "bridge"})
	if err != nil {
		log.Printf("error: %s", err.Error())
	}
	log.Printf("Create network ID: %s\n", networkResponse.ID)
}

func (c *Client) ListNetwork() {
	networkList, _ := c.cli.NetworkList(c.ctx, types.NetworkListOptions{})
	log.Println("Network List:")
	for number, network := range networkList {
		log.Printf("# %d, Name: %s", number, network.Name)
	}
}
