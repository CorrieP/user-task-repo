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

---

#### Create user
```sh
curl -i -H "Content-Type: application/json" -X POST -d '{"username":"jsmith","first_name" : "John", "last_name" : "Smith"}' http://localhost:8080/api/user
```

#### Update user
```
curl -i -H "Content-Type: application/json" -X PUT -d '{"first_name" : "John", "last_name" : "Doe"}' http://localhost:8080/api/user/{id}
```

#### List all users
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user
```

#### Get User info
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user/{id}
```
Expecting this structure (for the User):
```
{ 
  "id": 1,
  "username": "jsmith",
  "first_name": "James",
  "last_name": "Smith"
}
```

#### Create Task
```sh
curl -i -H "Content-Type: application/json" -X POST -d '{"name":"My task","description" : "Description of task", "date_time" : "2016-05-25 14:25:00"}' http://localhost:8080/api/user/{user_id}/task
```

#### Update Task
```sh
curl -i -H "Content-Type: application/json" -X PUT -d '{"name":"My updated task"}' http://localhost:8080/api/user/{user_id}/task/{task_id}
```

#### Delete Task
```sh
curl -i -H "Content-Type: application/json" -X DELETE http://localhost:8080/api/user/{user_id}/task/{task_id}
```

#### Get Task Info
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user/{user_id}/task/{task_id}
```

#### List all tasks for a user

```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user/{user_id}/task
```