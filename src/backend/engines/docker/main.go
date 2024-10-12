package main

import "log"

var (
	apiServer          *ApiServer
	KafkaCurrentClient *KafkaClient
)

func main() {
	log.Println("Start...")
	apiServer = NewApiServer()
	KafkaCurrentClient = NewKafkaClient()

	go func() {
		defer KafkaCurrentClient.Close()
		KafkaCurrentClient.Consume()
	}()

	apiServer.Run(":8083")
}
