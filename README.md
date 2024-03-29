# spring-boot-psql-in-docker

This repository contains the code and instructions for a demonstration on how to containerise a Spring Boot API, Postgres Database and pgAdmin. With each application in 3 separate containers.

## System Requirements

- Java JDK17+
- Maven
- Docker

## Starting off

First you need to clone the repository.

This can be done by HTTPS, SSH or GitHubCLI.

### HTTPS

```bash
git clone https://github.com/james-knott-iw/spring-boot-psql-in-docker.git
```

### SSH

```bash
git clone git@github.com:james-knott-iw/spring-boot-psql-in-docker.git
```

### GitHubCLI

```bash
gh repo clone james-knott-iw/spring-boot-psql-in-docker
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
mvn clean package -DskipTests
```

### Docker Compose File

In this project we need 3 applications running in 3 separate containers. Our Spring Boot API, the Postgres Database and pgAdmin dashboard. A Docker compose file helps to define multiple containers at once. There is one located in [/demo-api/compose.yaml](/demo-api/compose.yaml). Each container is defined as a `service`.

#### App

The first service defined is `app`. This service runs our Spring Boot API container.

- The service is based off an image called `spring-boot-psql-in-docker:latest`.
- The build context specifies that the image will be built using a Dockerfile within the same directory i.e. `/demo-api`. This Dockerfile will build the `spring-boot-psql-in-docker:latest` image.
- The `app` service `depends_on` the [db](#db) service. Therefore, the `app` service will start after the [db](#db) service service starts.
- There are three environment variables to set. `SPRING_DATASOURCE_URL` specifies the database URL so Spring knows where to connect to. `SPRING_DATA_SOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD` specify the credentials used to log into the database.
- Port `8080` on the host machine (your local machine) is mapped to port `8080` on the container. The Spring Boot application hosts the server on port `8080` on the container and this allows you to access it through port `8080` on your machine.

#### DB

The second service defined is `db`. This service runs our Postgres database container.

- This service uses the `postgres:13.1-alpine` image.
- `restart` is set to `always`. Once the container is started it will restart anytime it stops or fails.
- The `container_name` is set to `db` to allow for easy identification.
- Two environment variables are set. `POSTGRES_USER` and `POSTGRES_PASSWORD` allow you to define the user credentials for the Postgres database.

#### pgAdmin

The third service defined is `pgadmin`. This service runs our pgAdmin container which is an admin dashboard GUI for the Postgres DB.

- This service uses the `dpage/pgadmin4:latest` image.
- `restart` is set to `always`. Once the container is started it will restart anytime it stops or fails.
- Two environment variables are set. `PGADMIN_DEFAULT_EMAIL` and `PGADMIN_DEFAULT_PASSWORD` these are the admin credentials used to login to the pgAdmin dashboard.
- Port `5050` on the host machine(your machine) is mapped to to port `80` on the container. This will allow you to access the containers port `80` through port `5050` on your machine (the host).
- This service depends on the [db](#db) service. Therefore, `pgadmin` will start after the [db](#db) service is running.

### Running the Containers

To run the containers defined in [compose.yaml](/demo-api/compose.yaml):

```bash
docker-compose up -d
```

This command will run the containers in the background.

To stop and remove the containers:

```bash
docker-compose down
```

### Testing the Spring Boot API

- To test the Demo API, import the Postman requests JSON file [Person Requests.postman_collection.json](/demo-api/Demo%20API%20Requests.postman_collection.json). This Postman Collection contains requests to perform CRUD on Person and Pet Entities.  
- There is also an Open API Schema using Swagger UI. Once the API is running can be accessed at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Access the pgAdmin Dashboard

The pgAdmin dashboard is available at [http://localhost:5050](http://localhost:5050). The first time accessing the dashboard you will have to login.  

- Your email address will be whatever you defined in the [pgadmin](#pgadmin) service environment variable `PGADMIN_DEFAULT_EMAIL`.
- Your password will be whatever you defined in the [pgadmin](#pgadmin) service environment variable `PGADMIN_DEFAULT_PASSWORD`.

Once successfully logged in, you will be brought to the dashboard home page. To view the Postgres database defined in [db](#db), you will need to click `Add New Server`.

- In the `General` tab, give your server the name `db`.
- Navigate to the `Connection` tab.
- For `Host name/address` we can use the name of the Postgres service [db](#db).
- Make sure the port is `5432`.
- `Username` is the `POSTGRES_USER` defined in the [db](#db) service.
- `Password` is the `POSTGRES_PASSWORD` defined in the [db](#db) service.
- Then click save and you should see the `db` server under `Servers` in the `Object Explorer`.

Now if you look at `Databases`, you will see `compose-postgres` this is the Postgres database holding the `Person` and `Pet` tables for our Spring Boot API. Here you can explore the Postgres database and manage it using the admin UI.

## Access Prometheus

The Prometheus is a monitoring and alerting tool. It collects, stores and analyses metrics from various sources. Prometheus offers insights into performance and system health. In this case, the source is our API.

We're using the Prometheus official Docker image in this application. It is defined in [compose.yaml](/demo-api/compose.yaml). The port `9090` on the container is mapped to the host machines port `9090` which allows us to access to the application.

Once the Spring Boot API is packaged and the Docker containers are running the Prometheus dashboard can be accessed at [http://localhost:9090](http://localhost:9090).

## Access Grafana

Grafana is a visualisation, monitoringg and troubleshooting tool. IT creates dashboards to visualise metrics from sources like Prometheus. Grafana allows you to customise dashboards with graphs, tables, charts and much more.

Grafana will be running on a container based off the official Docker image. It is defined in [compose.yaml](/demo-api/compose.yaml). The port `3000` on the container is mapped to port `3000` on the host machine. This allows us to access the Grafana application at [http://localhost:3000](http://localhost:3000).

You will be brought to a login screen the first time accessing the dashboard. To login use the username - `admin` and password - `admin`. After logging in the first time you will be prompted to change your login details if you wish.
