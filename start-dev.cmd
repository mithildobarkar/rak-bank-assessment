@echo off

echo ============================================
echo Starting Microservices Development Environment
echo ============================================

echo Starting Eureka Server...
start Eureka Server cmd /k cd eureka-server && mvn spring-boot:run

timeout /t 10 > nul

echo Starting API Gateway...
start API Gateway cmd /k cd api-gateway && mvn spring-boot:run

timeout /t 5 > nul

echo Starting Student Service...
start Student Service cmd /k cd student-service && mvn spring-boot:run

timeout /t 5 > nul

echo Starting Fees Service...
start Fees Service cmd /k cd fees-service && mvn spring-boot:run

echo ============================================
echo All services started successfully!
echo ============================================

echo Eureka Dashboard: http://localhost:8761
echo Student Service H2 DB: http://localhost:8081/h2-console
echo username: sa
echo password: password

echo Fees Collection Service H2 DB: http://localhost:8082/h2-console
echo username: sa
echo password: password

pause