package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/google/uuid"
	"github.com/twmb/franz-go/pkg/kgo"
	"log"
)

type KafkaClient struct {
	client *kgo.Client
	ctx    context.Context
}

func NewKafkaClient() *KafkaClient {

	seeds := []string{"127.0.0.1:29092"}

	cl, err := kgo.NewClient(
		kgo.SeedBrokers(seeds...),
		kgo.ConsumerGroup("edu-group"),
		kgo.ConsumeTopics("edu_task_docker"),
	)
	if err != nil {
		panic(err)
	}

	return &KafkaClient{
		cl,
		context.Background(),
	}

}

func (kc *KafkaClient) Consume() {

	for {
		fetches := kc.client.PollFetches(kc.ctx)
		if errs := fetches.Errors(); len(errs) > 0 {
			panic(fmt.Sprint(errs))
		}

		fetches.EachPartition(func(p kgo.FetchTopicPartition) {
			p.EachRecord(func(record *kgo.Record) {
				task := &Task{}
				err := json.Unmarshal(record.Value, &task)
				if err != nil {
					log.Printf("ERROR: %v", err.Error())
				}
				TaskRun(task)
			})
		})
	}
}

func (kc *KafkaClient) Produce(message []byte) {
	id := uuid.New().String()
	record := &kgo.Record{Topic: "edu_results", Key: []byte(id), Value: message}

	if err := kc.client.ProduceSync(kc.ctx, record).FirstErr(); err != nil {
		log.Printf("record had a produce error while synchronously producing: %v\n", err)
	}
}

func (kc *KafkaClient) Close() {
	kc.client.Close()
}
