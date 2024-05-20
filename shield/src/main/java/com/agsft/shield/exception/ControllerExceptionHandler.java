package com.agsft.shield.exception;

import com.agsft.shield.dto.ResponseDTO;
import com.agsft.shield.exception.ShieldException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ShieldException.class)
    public ResponseEntity<?> shieldException(ShieldException shieldException){
        shieldException.printStackTrace();
        return ResponseEntity.internalServerError().body(new ResponseDTO(shieldException.getCode(), shieldException.getErrorList(), shieldException.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException runtimeException){
        return ResponseEntity.internalServerError().body(new ResponseDTO(403, runtimeException.getMessage(), "Error !!"));
    }
}
