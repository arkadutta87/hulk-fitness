package com.websystique.springmvc.payload;

/**
 * Created by arkadutta on 06/09/16.
 */
public class GenericResponse {

    private int code;
    private String message;

    public GenericResponse(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PackageDeleteResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
