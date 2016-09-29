package com.websystique.springmvc.bin;

import com.websystique.springmvc.common.InputEnum;
import com.websystique.springmvc.model.AppUser;
import com.websystique.springmvc.model.CustomerPackageEntity;
import com.websystique.springmvc.model.Member;
import com.websystique.springmvc.model.MemberPaginationObject;
import com.websystique.springmvc.utils.HibernateAnnotationUtil;
import com.websystique.springmvc.utils.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arkadutta on 30/09/16.
 */
public class UpdateMemberExpiryTime {

    private static SessionFactory sessionFactory = null;
    private static Session session = null;
    private static Transaction tx = null;
    private static final int timeLapse = -60;//days

    public static void main(String[] args) {

        System.out.println("The expiry date cron is working ... Yaay ... ");
        UpdateMemberExpiryTime aObj = new UpdateMemberExpiryTime();
        aObj.doWork();
    }

    public Member getMemberFromId(long id) {
        //Session session = getCurrentSession();
        Member m = null;
        try {
            m = (Member) session.load(Member.class, id);
            System.out.println("Member loaded -- " + m);
        } catch (Exception e) {
            e.printStackTrace();
            m = null;
        }


        return m;
    }

    private void doWork() {

        sessionFactory = HibernateAnnotationUtil.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        System.out.println("Session created");

        //start transaction
        tx = session.beginTransaction();

        Date currDt = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(currDt); // Now use today date.
        c.add(Calendar.DATE, timeLapse);//Add 100 year

        Date date = c.getTime();

        Criteria query = session.createCriteria(Member.class);
        query.add(Restrictions.le("latest_pkg_expiry", currDt));
        query.add(Restrictions.ge("latest_pkg_expiry", date));

        //sort based on updation_date

        query.addOrder(Order.asc("latest_pkg_expiry"));
        List<Member> members = query.list();

        System.out.println("The number of members -- " + members.size() + " --- ");
        for (Member aMem : members) {
            //System.out.println(aMem);
            long id = aMem.getId();
            //Session aSess = sessionFactory.getCurrentSession();
            Criteria query2 = session.createCriteria(CustomerPackageEntity.class);
            query2.add(Restrictions.eq("member_id", id));
            query2.add(Restrictions.gt("package_expiry_date", currDt));


            query2.addOrder(Order.asc("package_expiry_date"));
            query2.setFirstResult(0);
            query2.setMaxResults(2);

            List<CustomerPackageEntity> list = query2.list();
            if (!list.isEmpty()) {
                CustomerPackageEntity obj2 = list.get(0);
                Member obj3 = getMemberFromId(id);
                if (obj3 != null) {
                    obj3.setLatest_pkg_expiry(obj2.getPackage_expiry_date());
                    session.update(obj3);
                }
            }
        }

        tx.commit();
        if(session!= null){
            try{
                session.close();
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                session = null;
            }
        }
        HibernateAnnotationUtil.shutDown();
    }
}
