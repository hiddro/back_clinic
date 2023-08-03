package com.hanpeq.chavez.clinic.utils.commons;

import com.hanpeq.chavez.clinic.dto.ApiErrorError;
import com.hanpeq.chavez.clinic.security.exceptions.ApiError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@Component
@AllArgsConstructor
@ControllerAdvice
public class ExceptionHandler {

    public ApiErrorError builderApiError(String code, String message, String status, Map<String, Object> generalError){
        return ApiErrorError.builder()
                .code(code)
                .message(message)
                .status(status)
                .timestamp(generalError.get("timestamp").toString())
                .path(generalError.get("path").toString())
                .requestId(generalError.get("requestId").toString())
                .build();
    }

}
