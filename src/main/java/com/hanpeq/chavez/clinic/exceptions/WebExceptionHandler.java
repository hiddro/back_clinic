package com.hanpeq.chavez.clinic.exceptions;

import com.hanpeq.chavez.clinic.security.exceptions.ApiError;
import com.hanpeq.chavez.clinic.utils.commons.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(-1)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

     @Autowired
     private ExceptionHandler exceptionHandler;

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext,
                               ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request){
        Map<String, Object> generalError = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        Map<String, Object> customError = new HashMap<>();

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        String statusCode = String.valueOf(generalError.get("status"));
        Throwable error = getError(request);

        switch (statusCode){
            case "400":
                customError.put("error", exceptionHandler.builderApiError(statusCode, error.getMessage(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), generalError));
                status = HttpStatus.BAD_REQUEST;
                break;
            case "401":
                customError.put("error", exceptionHandler.builderApiError(statusCode, error.getMessage(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(), generalError));
                status = HttpStatus.UNAUTHORIZED;
                break;
            case "404":
                customError.put("error", exceptionHandler.builderApiError(statusCode, error.getMessage(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(), generalError));
                status = HttpStatus.NOT_FOUND;
                break;
            case "500":
                customError.put("error", exceptionHandler.builderApiError(statusCode, error.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), generalError));
                break;
        }

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(customError));
    }
}
