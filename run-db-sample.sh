#!/bin/sh

run_name=db-sample
previous_id=`docker ps | grep $run_name | awk '{print $1}'`

echo "previous running docker container id : "$previous_id

docker kill $previous_id
docker rm $previous_id

docker run -itd --name $run_name -p 8082:8080 -e "POSTGRES_ENV_IP=postgres" -e "POSTGRES_ENV_PORT=5432" -e "POSTGRES_ENV_DB=helm_sample" --link cache-sample:cache-sample --link postgres:postgres helm-sample/db-sample:1.0.0

