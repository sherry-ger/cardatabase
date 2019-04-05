# Car Database

A simple Spring Boot application that uses the H2 in memory database to store cars.

To start the application:

`./mvnw spring-boot:run`

This Service is dependent on https://github.com/bvader/car-value-estimator and should be started first.

If you enter a new Car with the brand of Ferrari it will cause a long running estimate in the car-value-estmator service.

To run on a host other than local you will need to update the `estimator.uri` property
`application.properties` file.

## To Deploy to Cloud foundry and Leverage Elastic APM

See the Cloud Foundry Elastic APM Document

https://github.com/cloudfoundry/java-buildpack/blob/master/docs/framework-elastic_apm_agent.md

Create the user provided service

`cf cups elastic-apm-service -p '{"server_urls":"https://my-apm-server:8200","secret_token":"my-secret-token"}'``

`cf push -f cardatabase-manifest.yml`

The front end React application is at: https://github.com/bvader/carfront
