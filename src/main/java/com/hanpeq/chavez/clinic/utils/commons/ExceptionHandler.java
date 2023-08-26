package com.hanpeq.chavez.clinic.utils.commons;

import com.hanpeq.chavez.clinic.dto.ErrorDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@Component
@AllArgsConstructor
@ControllerAdvice
public class ExceptionHandler {

    public ErrorDetail builderApiError(String code, String message, String status, Map<String, Object> generalError){
        return ErrorDetail.builder()
                .code(code)
                .message(message)
                .status(status)
                .timestamp(generalError.get("timestamp").toString())
                .path(generalError.get("path").toString())
                .requestId(generalError.get("requestId").toString())
                .build();
    }

}
