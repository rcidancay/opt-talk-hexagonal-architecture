#!/bin/sh
HOST=$1

curl -v -H "Connection: close" -X PUT "http://$HOST/mockserver/expectation" -d @vaccines/json/get-all-persons.json --header "Content-Type: application/json"
curl -v -H "Connection: close" -X PUT "http://$HOST/mockserver/expectation" -d @vaccines/json/get-all-vaccines.json --header "Content-Type: application/json"
curl -v -H "Connection: close" -X PUT "http://$HOST/mockserver/expectation" -d @vaccines/json/get-by-type-vaccines.json --header "Content-Type: application/json"
curl -v -H "Connection: close" -X PUT "http://$HOST/mockserver/expectation" -d @vaccines/json/put-vaccines.json --header "Content-Type: application/json"