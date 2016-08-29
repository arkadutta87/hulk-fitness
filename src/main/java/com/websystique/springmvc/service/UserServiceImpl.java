package com.websystique.springmvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.websystique.springmvc.dao.UserDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.websystique.springmvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserDao userDao;

    private static final AtomicLong counter = new AtomicLong();

    private static List<User> users;

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public User findByName(String name) {
        return userDao.findByName(name);
    }

    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    public void updateUser(User user) {

        /*User entity = dao.findById(user.getId());
        if(entity!=null){
            //entity.setSsoId(user.getSsoId());
            entity.setUsername(user.getUsername());
            entity.setAddress(user.getAddress());
            //entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            //entity.setUserProfiles(user.getUserProfiles());
        }*/
        try {
            userDao.updateUser(user);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void deleteUserById(int id) {

        userDao.deleteUserById(id);
    }

    public boolean isUserExist(User user) {
        return findByName(user.getUsername())!=null;
    }

    public void deleteAllUsers(){
        users.clear();
    }

}
