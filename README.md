# Starwars Project

> The main idea of this project is to test some techonologies and archicteture.
> Currently, we are using the [The Star Wars API] project for some tests.

The test API are in [Swagger].

### Stack technology

* Java 11
* Kotlin
* Spring Boot 2
* [Springdoc openapi]
  * springdoc-openapi java library helps automating the generation of API documentation using spring boot projects. springdoc-openapi works by examining an application at runtime to infer API semantics based on spring configurations, class structure and various annotations.
* Postgresql
* Lombok
  * Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
    Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.
* Logbook
  * Logbook is an extensible to enable complete request and response logging for different client- and server-side technologies.
* H2
  * H2 is a relational database management system. It can be embedded in Java applications or run in client-server mode.
* JUnit
  * JUnit is a unit testing framework. JUnit has been important in the development of TDD.
* Openfeign
  * [Feign] is a Java to HTTP client binder inspired by Retrofit, JAXRS-2.0, and WebSocket. Feign's first goal was reducing the complexity of binding Denominator uniformly to HTTP APIs regardless of ReSTfulness.
* Flyway
  * Flyway is an open-source database-migration tool.

### Installation

* Java 11 minimum version;
* Docker

### Plugins

* Lombok

### Deployment

* Local
  * Change the 'profiles' from the property at the **ENTRYPOINT**. Ex.: **-Dspring.profiles.active=dev**
  * Use the command to create the image: 
    * **docker build -t starwars-docker .**
    * **docker build --build-arg JAR_FILE=build/libs/\*.jar -t starwars-docker .**
  * Run the docker command: **docker run -p 9000:9000 starwars-docker**

[Swagger]: <http://localhost:9000/starwars-test/swagger-ui/index.html?configUrl=/starwars-test/v3/api-docs/swagger-config>
[Feign]: <https://github.com/OpenFeign/feign>
[The Star Wars API]: https://swapi.dev/
[Springdoc openapi]: https://springdoc.org/