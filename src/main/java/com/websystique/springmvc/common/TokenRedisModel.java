package com.websystique.springmvc.common;

/**
 * Created by arkadutta on 18/08/16.
 */
public class TokenRedisModel {

    private String username;
    private long id;

    public TokenRedisModel(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
