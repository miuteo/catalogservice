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
