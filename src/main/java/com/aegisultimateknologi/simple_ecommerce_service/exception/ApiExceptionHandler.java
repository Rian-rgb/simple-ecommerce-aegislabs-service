package com.aegisultimateknologi.simple_ecommerce_service.exception;

import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.*;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({
            BadRequestException.class,
            RoleNotFoundException.class,
            UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody DataResponse handleBadRequestException(BadRequestException e) {
       log.error(e.getMessage());
        return DataResponse.builder()
               .statusCode(HttpStatus.BAD_REQUEST.value())
               .errorMessage(e.getMessage())
               .build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody DataResponse handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return DataResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody DataResponse handleGenericException(Exception e) {
        log.error(e.getMessage());
        return DataResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody DataResponse handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String errorMessage = objectError.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return DataResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(String.join(", ", errors.values()))
                .build();
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody DataResponse handleInvalidPasswordException(InvalidPasswordException e) {
        log.error(e.getMessage());
        return DataResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler({
            UsernameAlreadyExistsException.class,
            EmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody DataResponse handleConfictException(InvalidPasswordException e) {
        log.error(e.getMessage());
        return DataResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .errorMessage(e.getMessage())
                .build();
    }
}
