#!/bin/sh

HOST="localhost:1080"

curl -v -X PUT "http:/$HOST/mockserver/reset"

sh vaccines/run.sh $HOST