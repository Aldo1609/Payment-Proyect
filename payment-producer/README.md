# Payment Producer Service API

This service is the entry point for all payment transactions. It's designed to be a lightweight and robust API that validates payment requests and enqueues them for asynchronous processing, ensuring a fast and responsive user experience.

---

## 🚀 Getting Started

To run this service, you'll need:

* Java 17+
* Maven
* PostgreSQL database
* A running Kafka instance

Follow these steps to set it up:

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/tu-usuario/payment-service.git](https://github.com/tu-usuario/payment-service.git)
    cd payment-service
    ```
2.  **Configure:** Update the database and Kafka connection details in `src/main/resources/application.properties`.
3.  **Run the application:**
    ```sh
    ./mvnw spring-boot:run
    ```

---

## 📋 API Endpoints

All API endpoints are documented using OpenAPI 3.1.0 and can be accessed through a Swagger UI at `/swagger-ui.html` once the application is running.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/payments` | Processes a payment request by validating the data and enqueuing it for asynchronous processing. |
| `GET` | `/healthcheck/liveness` | Verifies that the application is running. |
| `GET` | `/healthcheck/readiness` | Checks if the application is ready to handle requests. |

---

## 📐 Design & Architecture

This service follows a **producer-consumer pattern** to ensure non-blocking, asynchronous payment processing.

* **Producer Role:** This API acts as the **producer**. It receives the payment request, validates the data (including card validation via the **Luhn algorithm**), saves a pending transaction record in the database, and sends a message (containing only the transaction UUID) to a Kafka topic. It then responds immediately to the client.
* **Consumer Role:** A separate, dedicated service listens to the Kafka topic. It retrieves the transaction details from the database using the UUID and handles the communication with the external payment gateway, updating the transaction's status and logging the gateway's ID upon completion.

This architecture ensures high availability and resilience, as payment requests are not lost even if the external payment gateway or the consumer service are temporarily unavailable.

---

¿Do you want to know more about the project, or you want to contact me?

* **Linkedin:** https://www.linkedin.com/in/aldo-isaias-becerra-campos-591621200/
* **Mail:** aldobecerra1609@gmail.com