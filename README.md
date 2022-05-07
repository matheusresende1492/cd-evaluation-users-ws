#Application user-ws for cd evaluation

mvn package

docker build -t users-ws-docker-image .

docker-compose -f docker-compose.yaml up -d

kubectl apply -f deployment.yaml
