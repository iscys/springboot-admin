package com.project.model.school;

import lombok.Data;

@Data
public class ErrorModel {
    private String order_sn;
    private String error;
    private String detail;
    private String exceptions;

    public ErrorModel(String order_sn, String error, String detail) {
        this.order_sn = order_sn;
        this.error = error;
        this.detail = detail;
    }

    public ErrorModel(String order_sn, String error,String exceptions ,String detail) {
        this.order_sn = order_sn;
        this.error = error;
        this.detail = detail;
        this.exceptions = exceptions;

    }
}
