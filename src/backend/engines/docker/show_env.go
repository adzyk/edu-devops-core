package main

import (
	"context"
	"github.com/docker/docker/client"
	"log"
)

func (c *Client) PrintEnv() {
	cli, err := client.NewClientWithOpts(client.FromEnv)
	if err != nil {
		panic(err)
	}

	log.Printf("API Version: %s\n", cli.ClientVersion())
	ctx := context.Background()
	p, err := cli.Ping(ctx)
	if err != nil {
		log.Printf("Ping Docker Engine error: %s\n", err.Error())
	}
	log.Printf("API Version: %s, OSType: %s, Experimental: %t, BuilderVersion: %s\n",
		p.APIVersion, p.OSType, p.Experimental, p.BuilderVersion)

	info, _ := cli.Info(ctx)
	log.Printf("INFO\n CPUSet: %t, kernel version: %s\n", info.CPUSet, info.KernelVersion)

	log.Printf("Host address: %s\n", cli.DaemonHost())

	/*
		log.Printf("\n# Volumes #\n")
		vList, _ := cli.VolumeList(ctx, volume.ListOptions{})
		for number, nameVolume := range vList.Volumes {
			log.Printf("# %d - Name: %s", number, nameVolume.Name)
		}
	*/
}
