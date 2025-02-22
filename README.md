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

```shell
docker rm -fv polar-postgres
```
-f = force
-v = volumes, removes anonymous volumes attached to the container

|    Feature    |       Spring JDBC       |     Spring Data     |   Spring Data JPA    |
|:-------------:|:-----------------------:|:-------------------:|:--------------------:|
| Query method  | Raw SQL( JdbcTemplate)  | Custom Repositories | Auto-generated & JPL |
|  Flexibility  |          High           |       Medium        |    Low (uses ORM)    |
|    ORM dep    |            X            |          X          |         Yes          |


*@DataJdbcTest* <- It provides a focused, lightweight testing setup by configuring only the necessary beans for interacting with a database while excluding unnecessary Spring Boot components like web controllers or security.
it configures an in-memory database by default, but if you're using another external database, you can override this behaviour using *@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)*


#### The most common instrutions used in a Dockerfile for building container images

| Instruction |                                                                                                                                                          Description                                                                                                                                                           |                          Example                          |
|:-----------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------:|
|    FROM     |                                                                                                           Defines the base image for the subsequent instructions. It must be the first instruction in a docker file                                                                                                            |                     FROM ubuntu:22.04                     |
|    LABEL    |                                                                                                               Adds metadata to the image, following key-value format. Multiple LABEL instruction can be defined                                                                                                                |                   LABEL version:"1.2.1"                   |
|     ARG     |                                                                                                                  Defines a variable that user can pass at build time.Multiple ARGS instrutions can be defined                                                                                                                  |                       ARGS JAR_FILE                       |
|     RUN     |                                                                                                                        Executes the commands passed as arguments in a new layer on top of existing ones                                                                                                                        |   RUN apt-get update && apt-get install -y default-jre    |
| ENTRYPOINT  |                                                                Defines the program to execute when the image is run as a container. Only the last ENTRYPOINT instruction in a Dockerfile is considered. Each ENTRYPOINT declaration overrides the previous one.                                                                |             ENTRYPOINT ["java", "--version"]              |
|    COPY     |                                                                                                                        Copies files or directories from the host filesystem to the one inside container                                                                                                                        | COPY catalogservice-0.0.1-SNAPSHOT.jar catalogservice.jar |
|    USER     |                                                                                                              Defines the user that will run all the subsequent instructions and the image itself (as a container)                                                                                                              |                       USER sheldon                        |
|     CMD     | Specifies defaults for an executing container. If the ENTRYPOINT instruction is defined, they are passed as arguments. If not, it should also contain an executable. Only last CMD instruction in a Dockerfile is considered                                                                                         Specifies ||
|   WORKDIR   |                                                                                                       equivalent to `mkdir -p workspace && cd workspace`.Any subsequent commands will be executed inside this directory.                                                                                                       ||

#### RUN VS ENTRYPOINT
|     Feature      |                RUN                |                  ENTRYPOINT                   |
|:----------------:|:---------------------------------:|:---------------------------------------------:|
|  Execution time  |            build time             |                    runtime                    |
|     Purpose      | instal dependencies, compile code |   define the main process of the container    |
| Effects on image |  Commands modify the final image  | Commands executes every time container starts |

#### ENTRYPOINT VS CMD
|   Feature   |                        ENTRYPOINT                         |                          CMD                          |
|:-----------:|:---------------------------------------------------------:|:-----------------------------------------------------:|
|   Purpose   | Defines the main command that always run in the container | Provides the default command that can be *overridden* |
| Overridable |             No, unless `--entrypoint` is used             |        Yes, when passing a command at runtime         |


```shell
docker build -f .\docker\dockerfile.txt -t my-image:1.0.1 .  
docker run -p 8080:9001 my-image:1.0.1    
```
#### GitHub Container Registry -> publish your own container
```shell
docker login ghcr.io
```
```shell
docker tag my-image-published:1.0.1 ghcr.io/miuteo/my-image-published:1.0.1
docker push ghcr.io/miuteo/my-image-published:1.0.1     
```
```shell
docker network create catalog-network
```
```shell
docker run -d --name polar-postgres --network=catalog-network -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=polardb_catalog -p 5432:5432 postgres:14.4
```
```shell
docker build -f ./docker.file --build-arg JAR_FILE=target/*.jar -t catalog-service .
```
--build-arg to override ARG
```shell
docker run --net catalog-network -p 8080:9001 -e DB_HOSTNAME=polar-postgres --name=catalog-service  service-catalog-build:1.0.0
```
```shell
java -Djarmode=tools -jar .\catalogservice-0.0.1-SNAPSHOT.jar extract --layers --launcher
```
- `-Djarmode=tools` enables the spring boot tools mode instead of running the application normally
- `-jar` to without -jar java expects a class file
- `extract` command to be executed bt Spring Boot layered JARs
- `--layers` splits the JAR int separate layers (dependencies, resources, application code)
- `--launcher` if the loader is nto included, the extracted files will not be runnable as a normal Spring Boot app. with
launcher option we can simply run the springboot app with java org.springframework.boot.loader.JarLauncher

`FROM eclipse-temurin:17 AS builder`  <-staged build
`docker compose -p catalog down` -> -p project name. down = stop and delete
`docker inspect config-service`


### Chapter 7 - Kubernetes fundamentals
```shell
minikube start --cpus 2 --memory 4g --driver docker --profile polar
kubectl get nodes
```
NAME    STATUS   ROLES           AGE     VERSION
polar   Ready    control-plane   2m47s   v1.32.0

```shell
kubectl config get-contexts
kubectl config use-context <context-name>
 minikube delete --profile docker-desktop    
```
CURRENT   NAME    CLUSTER   AUTHINFO   NAMESPACE
*         polar   polar     polar      default
```shell
kubectl config current-context
```
```shell
kubectl config use-context polar
```

```shell
minikube stop --profile polar
minikube start --profile polar
minikube delete --profile polar
```
```ascii
+---------------------------------------------------------------+
|                Deployment                                     |
|  Manages ReplicaSets & Pods lifecycle                         |
|  +--------------------------------------------------------+   |
|  |               ReplicaSet                               |   |
|  | Ensures the right number of Pods                       |   |
|  |  +-------------+  +--------------+  +--------------+   |   |
|  |  |    Pod      |  |    Pod       |  |    Pod       |   |   |
|  |  | +---------+ |  | +----------+ |  | +----------+ |   |   |
|  |  | |Container| |  | |Container | |  | |Container | |   |   |
|  |  | +---------+ |  | +----------+ |  | +----------+ |   |   |
|  |  +-------------+  +--------------+  +--------------+   |   |
|  +--------------------------------------------------------+   |
+---------------------------------------------------------------+
```
```shell
cd ./kubernetes
kubectl apply -f services
kubectl get pod
kubectl logs deployment/polar-postgres
kubectl delete -f services

kubectl explain <object_name>
kubectl api-resources
```

```shell
minikube image load catalog-service --profile polar
kubectl apply -f k8s/deployment.yml
kubectl get all -l app=catalog-service
kubectl logs deployment/catalog-service
kubectl delete -f k8s/deployment.yml
kubectl get svc -l app=catalog-service
kubectl port-forward service/catalog-service 9001:80
kubectl delete pod <pod-name>
```
