#!/bin/bash

curl -X PUT http://localhost:8080/vaccines -d '{"vaccineType":"covid-19"}' -H 'Content-Type: application/json'