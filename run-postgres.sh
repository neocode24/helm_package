#!/bin/sh

run_name=postgres
previous_id=`docker ps | grep $run_name | awk '{print $1}'`
  
echo "previous running docker container id : "$previous_id

docker kill $previous_id
docker rm $previous_id

docker run -itd --name postgres -p 5432:5432 -e "POSTGRES_DB=helm_sample" -e "POSTGRES_USER=helm_user" -e "POSTGRES_PASSWORD=new1234!" -e "POSTGRES_INITDB_ARGS=--encoding=UTF-8" -v ~/data:/var/lib/postgresl/data $run_name

