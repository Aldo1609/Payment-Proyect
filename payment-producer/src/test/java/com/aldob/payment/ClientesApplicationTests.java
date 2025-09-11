package com.aldob.payment;

import com.aldob.payment.dto.PaymentDetailDTO;
import com.aldob.payment.dto.request.PaymentRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AllArgsConstructor
class ClientesApplicationTests {

	private Validator validator;

	@Test
	void shouldFailWhenNestedDtoIsInvalid() {
		PaymentRequestDTO request = new PaymentRequestDTO();
		request.setMonto(100.0);
		request.setDivisa("MXN");
		request.setClienteId("123");

		PaymentDetailDTO detalles = new PaymentDetailDTO();
		detalles.setNumeroTarjeta("invalid");
		detalles.setCvv("12");
		detalles.setFechaExpiracion("invalid");
		request.setDetallesPago(detalles);

		Set<ConstraintViolation<PaymentRequestDTO>> violations =
				validator.validate(request);

		assertThat(violations).hasSize(3);
	}

}
