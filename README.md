# Asynchronous Payment Processing System

This project is an asynchronous payment processing system designed to handle credit card transactions efficiently and robustly. The architecture is based on the **producer-consumer pattern**, utilizing **Spring Boot** and **Apache Kafka** to decouple services and ensure transactions are processed even during temporary failures.

---

## 🚀 Project Architecture

The project is organized into two main services, each with a specific responsibility:

1.  **`payment-producer`**: 
    This is the **entry point service** that exposes a REST API to receive payment requests. Its primary function is to **validate the data** (using algorithms like Luhn for card validation) and customer information. Once validated, it creates a transaction record in the database with a `PENDING` status and **sends the payment ID to a Kafka queue**. This allows the API to respond instantly to the client without waiting for payment gateway confirmation.

2.  **`payment-consumer`**:
    This service acts as the **background worker**. It listens to the Kafka topic, and upon receiving a payment ID, it's responsible for:
    - Retrieving the full transaction details from the database.
    - Communicating with the **external payment gateway** (e.g., Stripe or PayPal) to process the actual payment.
    - **Updating the payment's status** in the database to `COMPLETED` or `FAILED` based on the gateway's response.

---

## 📋 Technologies Used

* **Java 17**: Primary programming language.
* **Spring Boot**: Framework for building microservices.
* **Apache Kafka**: For asynchronous messaging between services.
* **PostgreSQL**: Database for storing payment and customer records.
* **Maven**: Dependency management tool.

---

## 🔧 Environment Setup

To run the complete project, ensure you have a running **PostgreSQL** and **Kafka** instance. The connection details for each service are configured in their respective `application.properties` files.

---

¿Do you want to know more about the project, or you want to contact me?

- Linkedin: https://www.linkedin.com/in/aldo-isaias-becerra-campos-591621200/
- Mail: aldobecerra1609@gmail.com
