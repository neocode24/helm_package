#!/bin/sh

run_name=redis
previous_id=`docker ps | grep $run_name | awk '{print $1}'`

docker kill $previous_id
docker rm $previous_id

echo "previous running docker container id : "$previous_id

docker run -itd -p 6379:6379 --name $run_name redis

