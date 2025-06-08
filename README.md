# Teleport-candidate-assessment
## Project Overview:
### Tracking number generator System
The Tracking Number Generator is a scalable and reactive microservice built with Spring Boot 3.4.6 and Java 17, designed to generate and persist unique tracking numbers. It leverages Spring WebFlux for non-blocking I/O and MongoDB for high-performance document storage.

#### Core Features
Generates unique 16-character alphanumeric tracking numbers based on:
* Origin country.
* Customer slug hash.
* Random suffix for uniqueness.
  Retries on duplicate tracking numbers up to 5 times

Stores metadata such as origin, destination, weight, and customer details

Fully reactive using Mono and Flux

Exception handling via centralized GlobalExceptionHandler

Unit-tested for service and repository layers

Dockerized for easy deployment

Automated CI/CD with GitHub Actions

#### Layered Architecture

Controller Layer
* This layer exposes RESTful APIs using Spring WebFlux. It handles incoming HTTP requests and delegates processing to the service layer.

Service Layer
* The service layer contains the core business logic. It handles tracking number generation, ensures uniqueness using a retry mechanism, and orchestrates the saving of data to MongoDB.

Transformer Layer
* Responsible for converting incoming request DTOs into MongoDB documents (TrackingDocument) and preparing response DTOs. This helps keep transformation logic out of the service layer.

Repository Layer
* Uses Spring Data's ReactiveMongoRepository to perform non-blocking database operations. This layer handles all interactions with MongoDB in a reactive and scalable manner.

#### Technology Stack

| Component      | Tech Used                   |
| -------------- |-----------------------------|
| Language       | Java 17                     |
| Framework      | Spring Boot 3.4.6 + WebFlux |
| Database       | MongoDB (Reactive)          |
| Packaging      | Docker                      |
| Build Tool     | Maven                       |
| CI/CD          | GitHub Actions              |
| Logging        | SLF4J + Logback             |
| Error Handling | @RestControllerAdvice       |
| Testing        | JUnit 5 + Mockito           |
| Security       | Spring Security             |
| Documentation  | Swagger / Springdoc OpenAPI |

Tracing and loggers
* Logback file with zipkin
  RUN `docker run -d -p 9411:9411 openzipkin/zipkin`  to UP the Zipkin Docker image




