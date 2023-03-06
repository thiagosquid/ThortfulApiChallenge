# ThortfulApiChallenge

This is an API developed for a job vacancy code challenge. The main objective is to create an API the 
consumes an 3rd part API services.

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org) 

## Running the Application
You can run the application by executing the main method in the 
`com.thiago.thortfulapichallenge.ThortfulApiChallengeApplication` class 
from your IDE.

or

Into the folder that contains the `pom.xml` file you can run the command below:
```shell
mvn spring-boot:run
```

### Running the tests
To run the tests you can execute the command bellow:
```shell
mvn test
```

## Swagger Documentation
After run the application you can see the swagger api documentation accessing the url 
`http://localhost:8080/api/swagger`