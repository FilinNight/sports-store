package com.company.store.dto;

import lombok.Getter;

@Getter
public class Response<T> {
    private T value = null;
    private Boolean isSuccessful = true;
    private String error = null;
    private Integer status = 200;

    private Response() {
    }

    public static <T> Response<T> ok(T value) {
        Response<T> response = new Response<>();
        response.value = value;
        return response;
    }

    public static <T> Response<T> ok(T value, Integer status) {
        Response<T> response = new Response<>();
        response.value = value;
        response.status = status;
        return response;
    }

    public static <T> Response<T> error(Exception e) {
        Response<T> response = new Response<>();
        response.error = getErrorMessage(e);
        response.status = 400;
        response.isSuccessful = false;
        return response;
    }

    public static <T> Response<T> error(Exception e, Integer status) {
        Response<T> response = new Response<>();
        response.error = getErrorMessage(e);;
        response.status = status;
        response.isSuccessful = false;
        return response;
    }

    private static String getErrorMessage(Exception e) {
        return "[" + e.getMessage() + "]: " + e.getMessage();
    }
}
