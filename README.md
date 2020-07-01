# origin-risk-api

This is a demo API constructed in Java using Spring Boot framework and Maven as the project management tool. It suggests an insurance plan based on their risk profile.

## How to run

### Installing Java
You need to have Java's environment configured. You can follow Oracle's instructions [here](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA).

### Installing Maven
You need to have Maven configured. You can follow its instructions [here](https://maven.apache.org/install.html).

### Compiling and Running
To compile the API, you can run the following command on the project root:
`mvn clean install -U`

It should compile the project and download any missing dependencies, generating a .jar file as a result inside the target folder.

Then, you can start the application by running:
`java -jar target\origin-risk-api-0.0.1-SNAPSHOT.jar`
It will start the application on localhost port 8080.
You can access its swagger on http://localhost:8080/swagger-ui.html or alternatively you can call the endpoint directly.

## Service

This API exposes the risk calculator on the endpoint **/risk/calculate**, which is a POST call.
It has two possible returns: a bad request error, in case the request is in any way invalid, and an ok response, with the suggested insurance plan.