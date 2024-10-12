package main

import (
	"github.com/gin-gonic/gin"
	"log"
)

type ApiServer struct {
	router *gin.Engine
}

func NewApiServer() *ApiServer {
	r := gin.Default()
	router(r)
	return &ApiServer{r}
}

func (s *ApiServer) Run(addr string) {
	err := s.router.Run(addr)
	if err != nil {
		log.Fatal(err)
	}
}

func router(router *gin.Engine) {
	//TODO добавить проверки запуска, возвращать разные коды
	router.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"status": "UP",
		})
	})
}
