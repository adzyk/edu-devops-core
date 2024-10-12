package main

import "github.com/docker/docker/api/types"

func (c *Client) ImageContent(name string) (*types.ImageInspect, error) {
	inspect, _, err := c.cli.ImageInspectWithRaw(c.ctx, name)
	if err != nil {
		return nil, err
	}
	return &inspect, nil
}
