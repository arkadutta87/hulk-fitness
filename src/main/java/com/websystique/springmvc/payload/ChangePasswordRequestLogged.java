package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 06/09/16.
 */
public class ChangePasswordRequestLogged {

    private String newPassword;

    public ChangePasswordRequestLogged() {

    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequestLogged{" +
                "newPassword='" + newPassword + '\'' +
                '}';
    }
}
