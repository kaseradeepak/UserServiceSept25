package com.scaler.userservicesept25.advices;

import com.scaler.userservicesept25.dtos.ExceptionDto;
import com.scaler.userservicesept25.exceptions.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDto> handleInvalidTokenException() {

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Unauthorized access, please try again with correct credentials");
        /// any number of params we can add in exception dto.

        return new ResponseEntity<>(
                exceptionDto,
                HttpStatus.UNAUTHORIZED
        );
    }
}
