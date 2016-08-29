package com.websystique.springmvc.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.websystique.springmvc.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao{

    private static final AtomicLong counter = new AtomicLong();

    private static List<User> users;

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAllUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User").list();

        for(User p : userList){
            System.out.println(p);
        }
        return userList;

        //Criteria criteria = createEntityCriteria().addOrder(Order.asc("username"));
        //criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        //List<User> users = (List<User>) criteria.list();

        // No need to fetch userProfiles since we are not showing them on list page. Let them lazy load.
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
        /*
        for(User user : users){
            Hibernate.initialize(user.getUserProfiles());
        }*/
        //return users;
    }

    @Override
    public User findById(int id) {
        //User user = getByKey(id);
        //return user;
        Session session = this.sessionFactory.getCurrentSession();
        User p = (User) session.load(User.class, new Integer(id));
        System.out.println("Person loaded successfully, Person details="+p);
        return p;
    }

    public User findByName(String name) {
        System.out.println("Username : "+name);

        Session session = this.sessionFactory.getCurrentSession();
        String hql = "from User u where u.username=:unameStr";
        System.out.println(hql);
        Query query = session.createQuery(hql);
        query.setParameter("unameStr", name);

        List<User> results = query.list();
        User user = null;
        if(results!= null && !results.isEmpty()){
            user = results.get(0);
        }
        //Criteria crit = createEntityCriteria();
        //crit.add(Restrictions.eq("username", name));
        //User user = (User)crit.uniqueResult();
        //System.out.println(user);
        /*if(user!=null){
            Hibernate.initialize(user.getUserProfiles());
        }*/
        return user;
    }

    @Override
    public void saveUser(User user) {
        try{
            //persist(user);
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(user);
            System.out.println("Person saved successfully, Person Details="+user);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {

        try{
            Session session = this.sessionFactory.getCurrentSession();
            session.update(user);
            System.out.println("Person updated successfully, Person Details="+user);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteUserById(int id) {

        /*Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("id", id));
        User user = (User)crit.uniqueResult();
        delete(user);*/
        Session session = this.sessionFactory.getCurrentSession();
        User p = (User) session.load(User.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        System.out.println("Person deleted successfully, person details="+p);
    }

    public boolean isUserExist(User user) {
        return findByName(user.getUsername())!=null;
    }

    public void deleteAllUsers(){
        users.clear();
    }

    /*private static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
        return users;
    }*/

}
