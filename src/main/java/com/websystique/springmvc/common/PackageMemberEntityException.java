package com.websystique.springmvc.common;

/**
 * Created by arkadutta on 18/09/16.
 */
public class PackageMemberEntityException extends  Exception{

    private String message;

    public PackageMemberEntityException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PackageMemberEntityException{" +
                "message='" + message + '\'' +
                '}';
    }
}
