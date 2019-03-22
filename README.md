# Car Database

A simple Spring Boot application that uses the H2 in memory database to store cars.

To start the application:

`./mvnw spring-boot:run`

This Service is dependent on https://github.com/bvader/car-value-estimator and should be started first.

If you enter a new Car with the brand of Ferrari it will cause a long running estimate in the car-value-estmator service.

The front end React application is at: https://github.com/bvader/carfront
