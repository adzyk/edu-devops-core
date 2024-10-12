FROM golang:1.22.5 AS build
WORKDIR /go/src/
COPY . .
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -ldflags="-w -s" -o case5 case5.go

FROM scratch

COPY --from=build /go/src/case5 .

ENTRYPOINT ["/case5"]
