# Payment Consumer Service

This service is the asynchronous worker responsible for processing payment transactions. It listens for messages from a Kafka queue, communicates with the external payment gateway, and updates the final status of each transaction.

---

## 🚀 Getting Started

To run this service, you'll need:

* Java 17+
* Maven
* PostgreSQL database
* A running Kafka instance (receiving messages from the `payment-producer` service)

Follow these steps to set it up:

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/tu-usuario/payment-consumer.git](https://github.com/tu-usuario/payment-consumer.git)
    cd payment-consumer
    ```
2.  **Configure:** Update the database and Kafka connection details in `src/main/resources/application.properties`. Ensure the `spring.kafka.consumer.topics` and `group-id` match your producer's configuration.
3.  **Run the application:**
    ```sh
    ./mvnw spring-boot:run
    ```

---

## 📋 Responsibilities

The primary role of this service is to handle the heavy lifting of the payment process. Its main responsibilities include:

* **Kafka Listener**: Actively listens to the designated Kafka topic for new payment messages. It processes one message at a time, preventing a bottleneck in the system.
* **Database Interaction**: Retrieves the full payment details from the PostgreSQL database using the unique UUID provided in the Kafka message.
* **Payment Gateway Integration**: Communicates with the external payment gateway's API to authorize and capture funds for the transaction.
* **Transaction Status Update**: Based on the response from the payment gateway, it updates the transaction record in the database, setting the status to `COMPLETED`, `FAILED`, or `REFUNDED` and storing the gateway's transaction ID for future reference.

---

## ⚙️ Design & Resiliency

This service is designed to be highly resilient.

* **Asynchronous Processing**: By consuming messages from a queue, the service can handle a high volume of transactions without being affected by network latency or gateway downtime.
* **Fault Tolerance**: If the service or the payment gateway fails, messages remain in the Kafka queue and can be reprocessed later, preventing data loss.
* **Scalability**: You can run multiple instances of this service to increase processing throughput, as Kafka will automatically distribute the workload among the consumer group.

---

¿Do you want to know more about the project, or you want to contact me?

* **Linkedin:** https://www.linkedin.com/in/aldo-isaias-becerra-campos-591621200/
* **Mail:** aldobecerra1609@gmail.com