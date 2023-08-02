package com.hanpeq.chavez.clinic.security.exceptions;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String code;
    private String message;
    private String status;
    private String timestamp;
    private String path;
    private String requestId;
}
