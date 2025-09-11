package com.aldob.payment.dto.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseDTO<T> {

    private Meta meta;
    private T data;
    public ApiResponseDTO(Meta meta) {
        this.meta = meta;
    }
}
