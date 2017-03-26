# belfastjug-sample-01
Sample API-Films for BelfastJUG presentation, showing an example of a Docker Containerised Microservice

## Get Started

Best way to get started is running it.
First, run you it locally, see if it's alright.
After, build your container image and run it in docker.
You can also, by the end of this process, import this as a generic Eclipse project to dig in.

The following steps will guide you through the process.

### 1. Clone this Repository
```
git clone git@github.com:hudsonmendes/belfastjug-sample-01.git
```

### 2. Run a MYSQL docker container
```
docker run --name=mysql --env="MYSQL_ROOT_PASSWORD=root" --publish 6603:3306 --detach mysql
```

### 3. Run a Gradle build for the dependencies
```
gradle clean cleanEclipse eclipse build --refresh-dependencies
```

### 4. Export the ENV variables required
```
export MYSQL_HOST=127.0.0.1
export MYSQL_PORT=3306
export MYSQL_LOGIN=root
export MYSQL_PASSWORD=root
```

### 5. Run your JAR
```
reset && java -jar build/libs/api-films-0.0.1-SNAPSHOT.jar
# hit CTRL+C to stop it
```

### 6. Build your Docker Image
```
docker build . -t belfastjug-api-films
```

### 7. Run your Docker Container
```
docker run --name "belfastjug-api-films" --publish 8080:8080 belfastjug-api-films
```
