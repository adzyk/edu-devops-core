package main

import (
	"fmt"
	"log"
	"net/http"
)

func main() {
	http.HandleFunc("/ping", func(w http.ResponseWriter, r *http.Request) {
		_, err := fmt.Fprintf(w, "pong")
		if err != nil {
			log.Printf("ERR: %v", err)
		}
	})

	log.Fatal(http.ListenAndServe(":8080", nil))
}
