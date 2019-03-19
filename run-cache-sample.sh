#!/bin/sh

run_name=cache-sample
previous_id=`docker ps | grep $run_name | awk '{print $1}'`

echo "previous running docker container id : "$previous_id

docker kill $previous_id
docker rm $previous_id

docker run -itd --name $run_name -p 8084:8080 -e "spring.redis.host=redis" -e "spring.redis.port=6379" --link redis:redis helm-sample/cache-sample:1.0.0

