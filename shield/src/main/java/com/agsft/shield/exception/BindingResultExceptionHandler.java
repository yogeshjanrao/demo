package com.agsft.shield.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class BindingResultExceptionHandler {

    public void bindingException(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ShieldException shieldException = new ShieldException();
            shieldException.setCode(HttpStatus.FORBIDDEN.value());
            shieldException.setMessage("Enter Valid Data !!");
            bindingResult.getFieldErrors().stream().forEach(bindingResults -> shieldException.addError(bindingResults.getField(), bindingResults.getDefaultMessage()));
            throw shieldException;
        }
    }

}
