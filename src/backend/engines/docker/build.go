package main

import (
	"archive/tar"
	"bytes"
	"github.com/docker/docker/api/types"
	"io"
	"os"
	"path/filepath"
)

type BuildRequest struct {
	DockerfileName string
	ImageName      string
	types.BuilderVersion
	dockerfile  string
	contextPath string
}

func (c *Client) Build(buildRequest BuildRequest) (*string, error) {

	dockerFileTarReader, err := loadContext(buildRequest)

	imageBuildResponse, err := c.cli.ImageBuild(
		c.ctx,
		dockerFileTarReader,
		types.ImageBuildOptions{
			Tags:       []string{buildRequest.ImageName},
			Context:    dockerFileTarReader,
			Dockerfile: buildRequest.DockerfileName,
			Version:    buildRequest.BuilderVersion,
			Remove:     true})

	if err != nil {
		return nil, err
	}
	defer imageBuildResponse.Body.Close()

	logBuffer := new(bytes.Buffer)
	_, err = io.Copy(logBuffer, imageBuildResponse.Body)
	if err != nil {
		return nil, err
	}
	log := logBuffer.String()

	return &log, nil
}

func loadContext(buildRequest BuildRequest) (*bytes.Reader, error) {

	buf := new(bytes.Buffer)
	tw := tar.NewWriter(buf)
	defer tw.Close()

	err := addDockerfile(buildRequest, tw)
	if err != nil {
		return nil, err
	}

	err = addContextFiles(buildRequest.contextPath, tw)
	if err != nil {
		return nil, err
	}

	return bytes.NewReader(buf.Bytes()), nil
}

func addDockerfile(buildRequest BuildRequest, tarWriter *tar.Writer) error {
	readDockerFile := []byte(buildRequest.dockerfile)

	tarHeader := &tar.Header{
		Name: buildRequest.DockerfileName,
		Size: int64(len(readDockerFile)),
	}

	err := tarWriter.WriteHeader(tarHeader)
	if err != nil {
		return err
	}
	_, err = tarWriter.Write(readDockerFile)
	if err != nil {
		return err
	}
	return nil
}

func addContextFiles(filePath string, tarWriter *tar.Writer) error {
	err := filepath.Walk(filePath,
		func(path string, info os.FileInfo, err error) error {
			if info.IsDir() {
				return nil
			}
			err = addFile(path, tarWriter)
			if err != nil {
				return err
			}
			return nil
		})
	if err != nil {
		return err
	}
	return nil
}

func addFile(filename string, tarWriter *tar.Writer) error {
	body, err := os.ReadFile(filename)
	if err != nil {
		return err
	}
	fileBase := filepath.Base(filename)
	tarHeader := &tar.Header{
		Name: fileBase,
		Size: int64(len(body)),
	}
	err = tarWriter.WriteHeader(tarHeader)
	if err != nil {
		return err
	}
	_, err = tarWriter.Write(body)
	if err != nil {
		return err
	}

	return nil
}
