package com.agsft.shield.dto;

public class ResponseDTO {

    private int code;
    private Object responseBody;
    private String message;

    public ResponseDTO() {
    }

    public ResponseDTO(int code, Object responseBody, String message) {
        this.code = code;
        this.responseBody = responseBody;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "ResponseDTO{" +
                "code=" + code +
                ", responseBody=" + responseBody +
                ", massage='" + message + '\'' +
                '}';
    }
}
