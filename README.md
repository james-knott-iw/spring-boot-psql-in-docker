# spring-boot-psql-in-docker

This repository contains the code and instructions for a demonstration on how to containerise a Spring Boot application, Postgres Database and Pgadmin. With each application in 3 seperate containers.


## System Requirements

- Java JDK17+
- Maven
- Docker

## Starting off

First you need to clone the repository.

This can be done by HTTPS, SSH or GitHubCLI.

### HTTPS

```
git clone https://github.com/IntegrationWorks/spring-boot-psql-in-docker.git
```

### SSH

```
git clone git@github.com:IntegrationWorks/spring-boot-psql-in-docker.git
```

### GitHubCLI
```
gh repo clone IntegrationWorks/spring-boot-psql-in-docker
```

Open the `/spring-boot-psql-in-docker` directory in a terminal or IDE and follow the next instructions in [Demo Api](#demo-api).


## Demo Api

To begin, enter the [/demo-api](/demo-api) directory:

```bash
cd demo-api
```

The next step is to package the spring boot application into a `.jar` file which will be located at `/target/demo-api-0.0.1.jar`:

```bash
mvn clean package
```
