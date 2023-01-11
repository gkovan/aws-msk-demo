#!/bin/bash
echo HELLO
#export AWS_SECRETS_PATH=secrets
export BOOTSTRAP_SERVERS=mskdemonoauth.8f1ofw.c2.kafka.us-east-1.amazonaws.com:9092,b-1.mskdemonoauth.8f1ofw.c2.kafka.us-east-1.amazonaws.com:9092,b-2.mskdemonoauth.8f1ofw.c2.kafka.us-east-1.amazonaws.com:9092
export PIPE_INPUT_TOPIC=streams-pipe-input
export PIPE_OUTPUT_TOPIC=streams-pipe-output
export APPLICATION_ID=-cloud9-streams-pipe