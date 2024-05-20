package com.agsft.shield.exception;

import com.agsft.shield.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ShieldException extends RuntimeException{

    private int code;
    private List<ResponseErrorDTO> errorList;
    private String message;

    public ShieldException() {
    }

    public ShieldException(int code, List<ResponseErrorDTO> errorList, String message) {
        this.code = code;
        this.errorList = errorList;
        this.message = message;
    }

    public ShieldException(HttpStatus code, String fieldName, String errorMassage, String message) {
        this.code = code.value();
        addError(fieldName,errorMassage );
        this.message = message;

    }

    public ShieldException(HttpStatus code, String message) {
        this.code = code.value();
        this.message = message;
    }

    public void addError(String fieldName, String errorMassage){
        if (this.errorList == null){
            this.errorList = new ArrayList<>();
        }
        this.errorList.add(new ResponseErrorDTO(fieldName, errorMassage));
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResponseErrorDTO> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ResponseErrorDTO> errorList) {
        this.errorList = errorList;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ShieldException{" +
                "code=" + code +
                ", errorList=" + errorList +
                ", message='" + message + '\'' +
                '}';
    }
}
