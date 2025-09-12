# API Gateway Service

This service acts as the **unified entry point** for all microservices in the architecture. Its primary goal is to provide centralized routing, security, and resilience, so individual microservices don't have to handle these concerns.

---

## 🚀 Key Features

Based on the configuration, this Gateway offers the following features:

1.  **Dynamic Routing**: Redirects incoming requests to the appropriate microservices (`payment-service`) using Eureka service registry.
2.  **Circuit Breaker**: Implements a **Circuit Breaker** pattern for the payment route (`/payments`). This isolates non-responsive or failing microservices, preventing the Gateway from overwhelming them and degrading the user experience.
3.  **Rate Limiting**: Controls the request rate per client (based on IP address) to prevent Denial-of-Service (DDoS) attacks and API abuse. The current configuration allows for **10 requests per second** with a burst capacity of **20**.
4.  **Rewrite Path**: Modifies the request paths to match the internal structure of the microservices, simplifying external URLs.
5.  **Service Discovery**: Registers as a **Eureka client** to discover and route requests to available microservice instances, ensuring high availability.

---

## 📋 Main Endpoints

| Path | Method | Description | Gateway Functionality |
| :--- | :--- | :--- | :--- |
| `/payment-service/api/v1/payments` | `POST` | Process a payment request. | **Rate Limiting** & **Circuit Breaker** |
| `/payment-service/api/v1/healthcheck/liveness` | `GET` | Verifies the health of the payment service. | Direct Routing |
| `/payment-service/api/v1/healthcheck/readiness`| `GET` | Verifies if the payment service is ready. | Direct Routing |

---

## ⚙️ Technologies & Configuration

* **Spring Cloud Gateway**: The core of the service.
* **Eureka Client**: For service discovery.
* **Redis**: Used as the backend for the **Rate Limiting** implementation.
* **Circuit Breaker**: Implemented with a library like Resilience4j.
* **YAML**: The primary configuration format.

---

## 🔧 Environment Setup

To run this service, ensure the following components are running:

* A **Eureka** server on `http://localhost:8761`.
* A **Redis** server on `localhost:6379`.
* The `payment-service` microservice, registered with Eureka.

```sh
# Example startup command
./mvnw spring-boot:run