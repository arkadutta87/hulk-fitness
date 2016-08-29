package com.websystique.springmvc.service;

import com.websystique.springmvc.model.AppUser;
import com.websystique.springmvc.model.Session;

/**
 * Created by arkadutta on 18/08/16.
 */
public interface LoginService {

    public AppUser getAppUser(String username, String password);

    public boolean saveSession(AppUser user, com.websystique.springmvc.model.Session session);

    public void updateUser(AppUser user);

    public AppUser getAppUserByUsername(String username);

    public Session getSession(String sessionStr);

    public void updateSession(com.websystique.springmvc.model.Session sess);

    public boolean isSessionActive(String sessionId);

    public AppUser getUserFromSession(String sessionId);
}

