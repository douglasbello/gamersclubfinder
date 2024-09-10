package com.gamersclubfinder.gamersclubfinder.dtos.handlers;

public class CustomErrorResponse {
    private String error;
    private String status;
    private int code;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(String error, String status, int code) {
        this.error = error;
        this.status = status;
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CustomErrorResponse{");
        sb.append("error='").append(error).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", code=").append(code);
        sb.append('}');
        return sb.toString();
    }
}