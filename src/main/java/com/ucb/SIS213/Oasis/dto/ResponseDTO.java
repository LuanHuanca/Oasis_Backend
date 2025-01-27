package com.ucb.SIS213.Oasis.dto;

public class ResponseDTO {
    private String code;
    private Object result;
    private String message;

    //usar en caso de obtener la respuesta correcta
    public ResponseDTO(Object result) {
        this.code = "200-OK";
        this.result = result;
    }
    //Usar en caso de error
    public ResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    //dame getters y setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    //toString
    @Override
    public String toString() {
        return "ResponseDTO [code=" + code + ", message=" + message + ", result=" + result + "]";
    }
}
