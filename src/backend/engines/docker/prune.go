package main

import (
	"github.com/docker/docker/api/types"
	"github.com/docker/docker/api/types/filters"
	"log"
)

func (c *Client) Prune() {
	log.Println("Start Prune")

	log.Println("Cache:")
	report, _ := c.cli.BuildCachePrune(c.ctx, types.BuildCachePruneOptions{All: true})
	for _, nameCache := range report.CachesDeleted {
		log.Printf("Name: %s\n", nameCache)
	}

	log.Println("Containers:")
	pruneReport, _ := c.cli.ContainersPrune(c.ctx, filters.Args{})
	for _, pruneContainer := range pruneReport.ContainersDeleted {
		log.Printf("Name: %s\n", pruneContainer)
	}

	log.Println("Volumes:")
	pruneVolumes, _ := c.cli.VolumesPrune(c.ctx, filters.Args{})
	for _, pruneVolume := range pruneVolumes.VolumesDeleted {
		log.Printf("Name: %s\n", pruneVolume)
	}

	log.Println("Images:")
	pruneImages, _ := c.cli.ImagesPrune(c.ctx, filters.Args{})
	for _, pruneImage := range pruneImages.ImagesDeleted {
		log.Printf("Name: %s\n", pruneImage)
	}

	log.Printf("Prune finish")
}
