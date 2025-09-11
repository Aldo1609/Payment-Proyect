package com.aldob.payment.consumer.listener;

import com.aldob.payment.consumer.service.PaymentProcessService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MessageListener {

    private final PaymentProcessService paymentProcessService;

    @KafkaListener(topics = "payments-topic", groupId = "payments-group")
    public void listen(String uuidPago){
        try {
            String cleanUuid = cleanUuidString(uuidPago);
            paymentProcessService.paymentProcess(cleanUuid);
        }catch (Exception e){
            log.error("Error processing payment: {}", uuidPago, e);
        }

    }

    private String cleanUuidString(String uuidString) {
        if (uuidString == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }

        return uuidString.replace("\"", "")
                .replace("'", "")
                .trim();
    }

}
