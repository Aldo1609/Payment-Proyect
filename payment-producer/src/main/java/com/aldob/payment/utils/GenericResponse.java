package com.aldob.payment.utils;

import com.aldob.payment.dto.payload.ApiResponseDTO;
import com.aldob.payment.dto.payload.Meta;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class GenericResponse<T> extends ResponseEntity<ApiResponseDTO<T>> {
    public GenericResponse(Meta meta, T data) {
        super(new ApiResponseDTO<>(meta, data), HttpStatusCode.valueOf(meta.getStatusCode()));
    }

    public GenericResponse(Meta meta) {
        super(new ApiResponseDTO<>(meta, null), HttpStatusCode.valueOf(meta.getStatusCode()));
    }
}

