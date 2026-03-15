#!/bin/bash

echo "Starting Development Environment..."
echo "Starting Eureka Server..."
cd eureka-server
mvn clean install &
mvn spring-boot:run &
EUREKA_PID=$!
cd ..

sleep 10

echo "Starting API Gateway..."
cd api-gateway
mvn clean install &
mvn spring-boot:run &
GATEWAY_PID=$!
cd ..

sleep 5

echo "Starting Student Service..."
cd student-service
mvn clean install &
mvn spring-boot:run &
STUDENT_PID=$!
cd ..

sleep 5

echo "Starting Fees Service..."
cd fees-service
mvn clean install
mvn spring-boot:run &
FEES_PID=$!
cd ..

echo ""
echo "All services started!"
echo ""
echo "Eureka Dashboard: http://localhost:8761"
echo ""
echo "Student Service H2 DB: http://localhost:8081/h2-console"
echo "username: sa"
echo "password: password"
echo ""
echo "Fees Collection Service H2 DB: http://localhost:8082/h2-console"
echo "username: sa"
echo "password: password"
echo ""

wait