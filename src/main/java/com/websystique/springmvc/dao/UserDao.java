package com.websystique.springmvc.dao;

import java.util.List;
import com.websystique.springmvc.model.User;

public interface UserDao {

    User findById(int id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(int id);

    List<User> findAllUsers();

    void deleteAllUsers();

    public boolean isUserExist(User user);
}
