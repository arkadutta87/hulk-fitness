package com.websystique.springmvc.service;

import com.websystique.springmvc.model.AppUser;
import com.websystique.springmvc.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public AppUser getAppUser(String username, String password) {
        System.out.println("Username : "+username + " - password - "+password);

        Session session = this.sessionFactory.getCurrentSession();
        String hql = "from AppUser u where u.username=:unameStr and u.password=:pwdStr";
        System.out.println(hql);
        Query query = session.createQuery(hql);
        query.setParameter("unameStr", username);
        query.setParameter("pwdStr", password);

        List<AppUser> results = query.list();
        AppUser user = null;
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

        //return null;
    }

    @Override
    public AppUser getAppUserByUsername(String username){
        System.out.println("Username : "+username );

        Session session = this.sessionFactory.getCurrentSession();
        String hql = "from AppUser u where u.username=:unameStr ";
        System.out.println(hql);
        Query query = session.createQuery(hql);
        query.setParameter("unameStr", username);
        //query.setParameter("pwdStr", password);

        List<AppUser> results = query.list();
        AppUser user = null;
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
    public void updateUser(AppUser user){
        try{
            Session session = this.sessionFactory.getCurrentSession();
            session.update(user);
            System.out.println("AppUser updated successfully, AppUser Details="+user);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveSession(AppUser user, com.websystique.springmvc.model.Session sessionObj){
        System.out.println("Saving session : "+sessionObj.getSession_id() + " - Username - "+user.getUsername());
        Session session = this.sessionFactory.getCurrentSession();
        //Transaction tx = session.beginTransaction();

        sessionObj.setUser(user);
        //user.getSessions().add(sessionObj);

        session.save(sessionObj);
        //tx.commit();


        return true;

    }

    @Override
    public com.websystique.springmvc.model.Session getSession(String sessionStr){
        System.out.println("Sessionstr : "+sessionStr );

        Session session = this.sessionFactory.getCurrentSession();
        String hql = "from Session u where u.session_id=:sessStr ";
        System.out.println(hql);
        Query query = session.createQuery(hql);
        query.setParameter("sessStr", sessionStr);
        //query.setParameter("pwdStr", password);

        List<com.websystique.springmvc.model.Session> results = query.list();
        com.websystique.springmvc.model.Session aSess = null;
        if(results!= null && !results.isEmpty()){
            aSess = results.get(0);
        }
        //Criteria crit = createEntityCriteria();
        //crit.add(Restrictions.eq("username", name));
        //User user = (User)crit.uniqueResult();
        //System.out.println(user);
        /*if(user!=null){
            Hibernate.initialize(user.getUserProfiles());
        }*/
        return aSess;
    }

    @Override
    public void updateSession(com.websystique.springmvc.model.Session sess){
        try{
            Session session = this.sessionFactory.getCurrentSession();
            session.update(sess);
            System.out.println("Session updated successfully, AppUser Details="+sess);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isSessionActive(String sessionId){
        if(sessionId.isEmpty()){
            return false;
        }else{
            com.websystique.springmvc.model.Session sess = getSession(sessionId);
            if(sess == null){
                return false;
            }else{
                return sess.is_enabled();
            }
        }
    }

    @Override
    public AppUser getUserFromSession(String sessionId){
        AppUser user = null;
        try{
            com.websystique.springmvc.model.Session sess = getSession(sessionId);
            //AppUser user = null;
            if(sess != null){
                user = sess.getUser();
                System.out.println("User came - getUserFromSession -- "+user);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            return user;
        }
    }
}
