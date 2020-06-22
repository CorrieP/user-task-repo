# Spring Boot Assessment
Basic implementation showcasing Spring Boot, JPA, REST, DTO, CRON components and Flyway Db Migrations
Used Lombok for cleaner code
Basic Docker containerization for testing (testcontainers) and local testing.

## Requirements
- gradle 6.0.1
- java 14
- docker


## Package the application
- Run tests
`$ ./gradlew test`
- Package the application
`$ ./gradlew bootjar`

## Run

`$ docker-compose build app`
`$ docker-compose up`

## Verify the application is running

> Application listens on port 8080
