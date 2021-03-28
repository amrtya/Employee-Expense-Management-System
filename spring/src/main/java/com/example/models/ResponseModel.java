package com.example.models;

public class ResponseModel {

    public static final String SUCCESS = "SUCCESS", FAILURE = "FAILURE", EMAIL_TAKEN = "EMAIL_TAKEN";

    private String responseType;
    private String message;

    public ResponseModel(String responseType) {
        this.responseType = responseType;
    }

    public ResponseModel(String responseType, String message) {
        this.responseType = responseType;
        this.message = message;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
