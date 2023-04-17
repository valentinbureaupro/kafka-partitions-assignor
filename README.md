# kafka-partitions-assignor

## Prerequisite

You need to install the following tools to be able to fully play with this project:
* java
* mvn
* docker
* minikube
* kubectl
* kcat


## Description

The aim of this project is to understand how Kafka partitions are assigned in Spring Boot applications during deployments on Kubernetes.
You will find below all the necessary instructions to run and play with it.
Two environment variables are available and customizable to discover and tweak different assignment options:
* `KAFKA_LISTENER_CONCURRENCY` (default: `1`) to define the number of threads
* `KAFKA_PARTITION_ASSIGNOR` (default: `org.apache.kafka.clients.consumer.RangeAssignor,org.apache.kafka.clients.consumer.CooperativeStickyAssignor`) to define the assignment strategy:
    * `org.apache.kafka.clients.consumer.RangeAssignor`
    * `org.apache.kafka.clients.consumer.RoundRobinAssignor`
    * `org.apache.kafka.clients.consumer.StickyAssignor`
    * `org.apache.kafka.clients.consumer.CooperativeStickyAssignor`


## Run locally

You can start/stop a local Kafka cluster by running the following commands:
```
docker-compose --file .docker-compose/docker-compose.yml up --detach
docker-compose --file .docker-compose/docker-compose.yml down
```

You can start the application by running the following commands:
```
mvn clean install
mvn spring-boot:run
```

Alternatively, you can build and run the dockerized application by running the following commands:
```
mvn clean install
docker build --no-cache --file .docker/Dockerfile . --tag valentinbureaupro/kafka-partitions-assignor
docker run --rm --name valentinbureaupro-kafka-partitions-assignor --network valentinbureaupro-network -e KAFKA_HOSTNAME=valentinbureaupro-kafka:9092 valentinbureaupro/kafka-partitions-assignor
```

Finally, you can send (produce) a new user message event on the `user.cdc` Kafka topic by running the following command:
```
kcat -b localhost:29092 -t user.cdc -P -H kafka_correlationId=6d490082-d629-11ed-afa1-0242ac120002 .data/user.json
```
The application is a log-base consumer, so you can see the consumed message in the logs.

More over, the `docker-compose` file also provides a complete local ELK stack to observe logs.
Indeed, the application generates logs that are collected by [Filebeat](https://www.elastic.co/beats/filebeat) and then ingested and transformed in [Logstash](https://www.elastic.co/logstash).
Then, the latter sends logs to [Elasticsearch](https://www.elastic.co/elasticsearch) to store them in different indices.
Finally, [Kibana](https://www.elastic.co/kibana) offers a complete web interface to observe, search and visualize the stored logs!

Thus, you can open a browser and access to Kibana at http://localhost:25601 to observe your logs ! 🎉
The ELK components are only compatible with dockerized `kafka-partitions-assignor` application launches (see provided docker commands above).


## Run on Kubernetes ([minikube](https://minikube.sigs.k8s.io/))

First, you can build the dockerized application by running the following commands:
```
mvn clean install
docker build --no-cache --file .docker/Dockerfile . --tag valentinbureaupro/kafka-partitions-assignor
```

Then, you can start/stop a local Kubernetes cluster by running the following commands:
```
minikube start
minikube delete
```

And add all docker images to minikube with the following commands:
```
minikube image load confluentinc/cp-zookeeper:7.3.3 confluentinc/cp-kafka:7.3.3
minikube image load docker.elastic.co/elasticsearch/elasticsearch:8.7.0 docker.elastic.co/logstash/logstash:8.7.0 docker.elastic.co/beats/filebeat:8.7.0 docker.elastic.co/kibana/kibana:8.7.0
minikube image load valentinbureaupro/kafka-partitions-assignor
```

Next, you can deploy the complete stack in a Kubernetes cluster with the following command:
```
kubectl apply -f .kubernetes --recursive --prune --all
```

Finally, you can send (produce) a new user message event on the `user.cdc` Kafka topic by running the following commands:
```
kubectl port-forward statefulset/kafka 29092:29092 --namespace kafka
kcat -b localhost:29092 -t user.cdc -P -H kafka_correlationId=6d490082-d629-11ed-afa1-0242ac120002 .data/user.json
```
The application is a log-base consumer, so you can see the consumed message in the logs.

More over, the Kubernetes cluster also provides a complete ELK stack in the `monitoring` namespace to observe logs.
Indeed, the application generates logs that are collected by [Filebeat](https://www.elastic.co/beats/filebeat) and then ingested and transformed in [Logstash](https://www.elastic.co/logstash).
Then, the latter sends logs to [Elasticsearch](https://www.elastic.co/elasticsearch) to store them in different indices.
Finally, [Kibana](https://www.elastic.co/kibana) offers a complete web interface to observe, search and visualize the stored logs!

Last but not least, we need to add to our Kubernetes cluster (minikube) an ingress controller. It is configured out of the box on cloud providers, but provided as addons on minikube (Nginx by default).
In the end, we have to register our local host domain name `kibana.valentinbureaupro` with our local Kubernetes cluster IP.
You can do all of that with the following commands:

MacOS:
```
minikube addons enable ingress
echo "127.0.0.1 kibana.valentinbureaupro" | sudo tee -a /etc/hosts
minikube tunnel
```
Linux:
```
minikube addons enable ingress
echo "$(minikube ip) kibana.valentinbureaupro" | sudo tee -a /etc/hosts
```

Thus, you can open a browser and access to Kibana at http://kibana.valentinbureaupro to observe your logs ! 🎉

Alternatively, you can also access to Kibana by running the following command:
```
minikube service kibana --namespace monitoring
```
