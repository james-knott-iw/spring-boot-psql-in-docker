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


## Demo API

To begin, enter the [/demo-api](/demo-api) directory:

```bash
cd demo-api
```
### Package Spring Boot API into JAR file

The next step is to package the spring boot application into a `.jar` file which will be located at `/target/demo-api-0.0.1.jar`:

```bash
mvn clean package
```


### Docker Compose

In this project we need 3 applications running in 3 seperate containers. Our Spring Boot API, the Postgres Database and Pgadmin dashboard. A Docker compose file helps to define multiple containers at once. There is one located in [/demo-api/compose.yaml](/demo-api/compose.yaml). Each container is defined as a `service`.

#### App

The first service defined is `app`. This service runs our Spring Boot application container. 
- The service is based off a image called `spring-boot-psql-in-docker:latest`.
- The build context specifes that the image will be built using a Dockerfile within the same directory i.e. `/demo-api`.
- The `app` service `depends_on` the [db](#db) service. Therefore, the `app` service will start after the [db](#db) service service starts.
- There are a three environment variables to set. `SPRING_DATASOURCE_URL` specifies the database URL so Spring knows where to connect to. `SPRING_DATA_SOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD` specify the credentials used to log into the database.
- Port `8080` on the host machine (your local machine) is mapped to port `8080` on the container. The Spring Boot application hosts the server on port `8080` on the container and this allows you to access it through port `8080` on your machine.


#### DB
The second service defined is `db`. This service runs our Postgres database container.

- This service uses the `postgres:13.1-alpine` image.
- `restart` is set to `always`. Once the container is started it will restart anytime it stops or fails.
- The `container_name` is set to `db` to allow for easy identification.
- Two environment variables are set. `POSTGRES_USER` and `POSTGRES_PASSWORD` allow you to define the user credentials for the Postgres database.


#### Pgadmin

The third service defined is `pagadmin`. This service runs our Pgadmin container which is an admin dashboard GUI for the Postgres DB.

- This service uses the `dpage/pgadmin4:latest` image.
- `restart` is set to `always`. Once the container is started it will restart anytime it stops or fails.
- Two environment variables are set. `PGADMIN_DEFAULT_EMAIL` and `PGADMIN_DEFAULT_PASSWORD` these are the admin credentials used to login to the pgadmin dashboard. Instructions are in ... TODO
- Port `5050` on the host machine(your machine) is mapped to to port `80` on the container. This will allow you to access the containers port `80` through port `5050` on your machine (the host).
- This service depends on the [db](#db) service. Therfore, `pgadmin` will start after the [db](#db) service is running.


### Running the Containers

To run these containers defined in [compose.yaml](/demo-api/compose.yaml):

```bash
docker compose up -d
```
This command will run the containers in the background.


### Testing the API
- To test the Demo API, import the Postman requests JSON file [Person Requests.postman_collection.json](/demo-api/Demo%20API%20Requests.postman_collection.json). This Postman Collection contains requests to perform CRUD on Person and Pet Entities.  
- There is also an Open API Schema using Swagger UI. Once the API is running can be accessed at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


### Access Pgadmin Dashboard
The Pgadmin dashboard is available at [http://localhost:5050](http://localhost:5050). The first time accessing the dashboard you will have to login.  

- Your email address will be whatever you defined in the [pgadmin](#pgadmin) service environment variable `PGADMIN_DEFAULT_EMAIL`.
- Your password will be whatever you defined in the [pgadmin](#pgadmin) service environment variable `PGADMIN_DEFAULT_PASSWORD`.

Once successfully logged in, you will be brought to the dashboard home page. To view the Postgres database defined in [db](#db), you will need to click `Add New Server`. 

- In the `General` tab, give your server the name `db`.
- Navigate to the `Connection` tab.
- For the `Host name/address` we can use the name of the Postgres service [db](#db).
- Make sure the port is `5432`.
- `Username` is the `POSTGRES_USER` defined in the [db](#db) service.
- `Password` is the `POSTGRES_PASSWORD` defined in the [db](#db) service.
- Then click save and you should see the `db` server under `Servers` in the `Object Explorer`. 

Now if you look at `Databases`, you will see `compose-postgres` this is the Postgres database holding the Person and Pet tables for our Spring Boot API.

