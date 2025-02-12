# Getting Started

### Chapter 2
mvn spring-boot:build-image

docker images catalogservice:0.0.1-SNAPSHOT
REPOSITORY       TAG              IMAGE ID       CREATED        SIZE
catalogservice   0.0.1-SNAPSHOT   e9d7a07f9f65   45 years ago   469MB

docker run --rm --name catalog-service -p 8080:8080 catalogservice:0.0.1-SNAPSHOT

minikube start
106gb
minikube image load catalogservice:0.0.1-SNAPSHOT

kubectl create deployment catalog-service --image=catalogservice:0.0.1-SNAPSHOT
kubectl get deployment
kubectl get pod
kubectl logs deployment/catalog-service
kubectl expose deployment catalog-service --name=catalog-service-expose-name --port=8080
kubectl get service catalog-service-expose-name
kubectl port-forward service/catalog-service-expose-name 8000:8080

kubectl delete deployment catalog-service
kubectl delete service catalog-service-expose-name
minikube stop


### Chapter3
#### spring stuff
to access spring application properties:
Environment interface, @Value, @ConfigurationProperties

@ConfigurationProperties(prefix = "polar") requires Not registered via @EnableConfigurationProperties, marked as Spring component, or scanned via @ConfigurationPropertiesScan

| VS |                @EnableConfigurationProperties                |                     @ConfigurationPropertiesScan                     |     
|----|:------------------------------------------------------------:|:--------------------------------------------------------------------:|
|    |    @EnableConfigurationProperties(PolarProperties.class)     |                     @ConfigurationPropertiesScan                     |
|    |    Requires **manual** registration of properties classes    | **Automatically** scans for all **@ConfigurationProperties** classes |
|    |  Common in **Spring Boot starters** and auto-configurations  |              More common in **application-level code**               |

precedence for overriding a spring property:
cli argument > jvm property > enviroment variable > property file > default(if any)
 - cli: java -jar target/catalogservice-0.0.1-SNAPSHOT.jar --polar.greeting="Welcome to the catalog from CLI
 - jvm property:  java -Dpolar.greeting="Welcome to the catalog from JVM" -jar target/catalogservice-0.0.1-SNAPSHOT.jar
 - env variable: $env:POLAR_ GREETING="Welcome to the catalog from ENV"; java -jar target/catalogservice-0.0.1-SNAPSHOT.jar

http POST :9001/actuator/refresh

```shell
docker run -d --name polar-postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=polardb_catalog -p 5432:5432 postgres:14.4
```