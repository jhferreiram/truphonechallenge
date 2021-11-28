# TruPhoneChallenge
This repository contains the source code for the Devices MS, a REST API responsible to manage Devices.

### Table of contents
- [Tech Stack](#tech-stack)
- [Overall Architecture](#overall-architecture)
- [Development Setup](#development-setup)
- [How to Use the API](#how-to-use-the-api)

## Tech Stack
- Java 11
- Postgres
- Maven
- Docker

## Overall Architecture

This Application is composed by 5 modules with different responsibilities:

### Application Module
Main module responsible for the startup of the application and also containing all the integration tests (end-to-end)

### Rest Module
Contains all the Rest interfaces that allow using and manage the api

### Core Module
Contains all the business logic and error handling

### Domain Module
Contains all business models and data transfer objects (DTO)

### Data Provider Module
Contains integrations with external persistence like datasources, web services, ...

## Development Setup

### Run tests with docker
```
docker-compose up tests
```

### Start API
```
docker-compose up dev
```

## How to Use the API

### With Swagger
Access Swagger documentation in: ${host}/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

### With Postman
Import Challenge_postman_collection.json and start using the API