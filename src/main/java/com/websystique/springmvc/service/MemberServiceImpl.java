package com.websystique.springmvc.service;

import com.websystique.springmvc.model.*;
import com.websystique.springmvc.model.Package;
import com.websystique.springmvc.payload.CustomerPackageEntityPaginationObject;
import com.websystique.springmvc.payload.CustomerPackageEntityPayLoad;
import com.websystique.springmvc.payload.MemberFilter;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by arkadutta on 06/09/16.
 */
@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public MemberPaginationObject getMember(int step, MemberFilter filter, int count) {
        int pageSize = count;
        Session session = getCurrentSession();

        /*String countQ = "Select count (p.id) from Member p";
        Query countQuery = session.createQuery(countQ);
        Long countResults = (Long) countQuery.uniqueResult();*/

        String nameStr = filter.getNameStr();
        String mobileStr = filter.getMobileStr();

        Criteria countQuery = session.createCriteria(Member.class);
        if (nameStr != null && !(nameStr.trim().isEmpty())) {

            //hql += "u.name like ? or u.specialName like ? ";
            Criterion q1 = Restrictions.ilike("firstName", nameStr, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.ilike("lastName", nameStr, MatchMode.ANYWHERE);
            Criterion q3 = Restrictions.ilike("email", nameStr, MatchMode.ANYWHERE);
            countQuery.add(Restrictions.or(q1, q2, q3));

        }

        if (mobileStr != null && !(mobileStr.trim().isEmpty())) {
            Criterion q1 = Restrictions.ilike("mobile", mobileStr, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.ilike("alternateMobile", mobileStr, MatchMode.ANYWHERE);
            countQuery.add(Restrictions.or(q1, q2 ));

        }
        //countQuery.add(Restrictions.eq("isEnabled",true));
        countQuery.setProjection(Projections.rowCount());
        Long countResults = (Long) countQuery.uniqueResult();

        //String countQ = "Select count (p.id) from Package p";
        //Query countQuery = session.createQuery(countQ);
        //Long countResults = (Long) countQuery.uniqueResult();

        if(countResults == 0){
            MemberPaginationObject obj = new MemberPaginationObject();
            obj.setTotalCount(countResults);
            obj.setPackages(new ArrayList<Member>());

            return obj;
        }

        int start = -1;

        if((step-1) * count >= countResults){
            System.out.println("#################### Possible attempt to hack in #############");
            return null;
        }

        start = (step-1)*count  ;

        //String hql = "from Package u where ";//where u.username=:unameStr and u.password=:pwdStr";
        Criteria query = session.createCriteria(Member.class);
        if (nameStr != null && !(nameStr.trim().isEmpty())) {

            //hql += "u.name like ? or u.specialName like ? ";
            Criterion q1 = Restrictions.ilike("firstName", nameStr, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.ilike("lastName", nameStr, MatchMode.ANYWHERE);
            Criterion q3 = Restrictions.ilike("email", nameStr, MatchMode.ANYWHERE);
            query.add(Restrictions.or(q1, q2, q3));

        }

        if (mobileStr != null && !(mobileStr.trim().isEmpty())) {
            Criterion q1 = Restrictions.ilike("mobile", mobileStr, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.ilike("alternateMobile", mobileStr, MatchMode.ANYWHERE);
            query.add(Restrictions.or(q1, q2 ));

        }

        //sort based on updation_date

        query.addOrder(Order.asc("latest_pkg_expiry"));
        query.setFirstResult(start);
        query.setMaxResults(count);
        List<Member> members = query.list();

        System.out.println("The number of packages -- "+members.size() + " --- ");

        MemberPaginationObject obj = new MemberPaginationObject();
        obj.setTotalCount(countResults);
        obj.setPackages(members);

        return obj;
    }

    @Override
    public MemberPaginationObject getMember(int step, int count,int timeExpiry ) {
        int pageSize = count;
        Session session = getCurrentSession();

        Date currDt = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(currDt); // Now use today date.
        c.add(Calendar.DATE, timeExpiry);//Add 100 year

        Date date = c.getTime();



        Criteria countQuery = session.createCriteria(Member.class);
        countQuery.add(Restrictions.le("latest_pkg_expiry",date));

        //countQuery.add(Restrictions.eq("isEnabled",true));
        countQuery.setProjection(Projections.rowCount());
        Long countResults = (Long) countQuery.uniqueResult();

        //String countQ = "Select count (p.id) from Package p";
        //Query countQuery = session.createQuery(countQ);
        //Long countResults = (Long) countQuery.uniqueResult();

        if(countResults == 0){
            MemberPaginationObject obj = new MemberPaginationObject();
            obj.setTotalCount(countResults);
            obj.setPackages(new ArrayList<Member>());

            return obj;
        }

        int start = -1;

        if((step-1) * count >= countResults){
            System.out.println("#################### Possible attempt to hack in #############");
            return null;
        }

        start = (step-1)*count  ;

        //String hql = "from Package u where ";//where u.username=:unameStr and u.password=:pwdStr";
        Criteria query = session.createCriteria(Member.class);
        countQuery.add(Restrictions.le("latest_pkg_expiry",date));

        //sort based on updation_date

        query.addOrder(Order.asc("latest_pkg_expiry"));
        query.setFirstResult(start);
        query.setMaxResults(count);
        List<Member> members = query.list();

        System.out.println("The number of packages -- "+members.size() + " --- ");

        MemberPaginationObject obj = new MemberPaginationObject();
        obj.setTotalCount(countResults);
        obj.setPackages(members);

        return obj;
    }

    @Override
    public void saveMember(Member mem) {
        Session session = getCurrentSession();
        session.persist(mem.getAddress());
        System.out.println("Member added successfully, Member Details= "+mem);
    }

    @Override
    public void editMember(Member mem) {

        Session session = getCurrentSession();
        session.update(mem);
        System.out.println("Update Member call - "+mem);

    }//editAddress

    @Override
    public void editCustomerPackageEntity(CustomerPackageEntity mem) {

        Session session = getCurrentSession();
        session.update(mem);
        System.out.println("Update CustomerPackageEntity call - "+mem);

    }

    @Override
    public void editAddress(Address addr) {

        Session session = getCurrentSession();

        session.update(addr);
        System.out.println("Update Address call - "+addr);

    }

    @Override
    public Member getMemberFromId(long id){
        Session session = getCurrentSession();
        Member m = null;
        try{
            m = (Member) session.load(Member.class, id);
            System.out.println("Member loaded -- "+m);
        }catch(Exception e){
            e.printStackTrace();
            m = null;
        }


        return m;
    }

    @Override
    public int getMemberPackageUtilizationPercentage(long id){
        Session session = getCurrentSession();

        Criteria query = session.createCriteria(CustomerPackageEntity.class);
        Criterion q1 = Restrictions.eq("member_id", id);
        query.add(q1);
        Criterion q2 = Restrictions.ge("package_expiry_date", new Date());
        query.add(q2);
        query.add(Restrictions.eq("isEnabled",true));

        query.addOrder(Order.asc("package_expiry_date"));
        query.setFirstResult(0);
        query.setMaxResults(10);
        List<CustomerPackageEntity> objects = query.list();

        if(objects!= null && !objects.isEmpty()){
            CustomerPackageEntity aObj = objects.get(0);

            Date start = aObj.getPackage_enrollment_date();
            Date end  = aObj.getPackage_expiry_date();

            long total_days = TimeUnit.DAYS.convert(end.getTime() - start.getTime(), TimeUnit.MILLISECONDS);
            long remaining_days = TimeUnit.DAYS.convert(end.getTime() - (new Date()).getTime(), TimeUnit.MILLISECONDS);

            System.out.println("Total days -- "+ total_days + " -- remaining days -- "+remaining_days);

            double percentage_elapsed = ((int)total_days - (int)remaining_days )/ (double)total_days * 100;
            System.out.println("Utilization percentage -- "+ percentage_elapsed);
            return new Double(percentage_elapsed).intValue();
            //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        }else{
            Criteria query2 = session.createCriteria(CustomerPackageEntity.class);
            Criterion q12 = Restrictions.eq("member_id", id);
            query2.add(q12);
            query2.add(Restrictions.eq("isEnabled",true));
            query2.addOrder(Order.asc("package_expiry_date"));
            query2.setFirstResult(0);
            query2.setMaxResults(2);
            List<CustomerPackageEntity> objects2 = query2.list();

            if(objects2 == null || objects2.isEmpty()){
                return 0;
            }else{
                return 100;
            }

        }
    }

    private Package getPackageFromId(long id){
        Session session = getCurrentSession();
        Package p = null;
        try{
            p = (Package) session.load(Package.class, id);
            System.out.println("Package loaded -- "+p);
        }catch(Exception e){
            e.printStackTrace();
            p = null;
        }


        return p;
    }

    public CustomerPackageEntity getCustomerPackageEntityFromId(long id){
        Session session = getCurrentSession();
        CustomerPackageEntity p = null;
        try{
            p = (CustomerPackageEntity) session.load(CustomerPackageEntity.class, id);
            System.out.println("CustomerPackageEntity loaded -- "+p);
        }catch(Exception e){
            e.printStackTrace();
            p = null;
        }


        return p;

    }

    private String convertToStrDate(Date obj){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");//yyyy-MM-dd HH:mm:ss.S
        String str = "";
        try{
            str = simpleDateFormat.format(obj);
        }catch (Exception e){
            e.printStackTrace();
            //dob = new Date();
        }

        return str;
    }

    @Override
    public void saveCustomerPackageEntity(CustomerPackageEntity obj){
        Session session = getCurrentSession();
        session.persist(obj);
        System.out.println("CustomerPackageEntity added successfully, CustomerPackageEntity Details= "+obj);
    }

    @Override
    public Date getExpiryDateAssociatedWithMember(long memberId,long custPkgEntityId){
        Session session = getCurrentSession();

        Date adt = new Date();

        //check the right half
        Criteria query = session.createCriteria(CustomerPackageEntity.class);
        Criterion q11 = Restrictions.eq("member_id", memberId);
        query.add(q11);
        query.add(Restrictions.eq("isEnabled",true));
        query.add(Restrictions.ne("id",custPkgEntityId));
        query.add(Restrictions.ge("package_expiry_date", adt));
        query.addOrder(Order.asc("package_expiry_date"));

        query.setFirstResult(0);
        query.setMaxResults(2);
        List<CustomerPackageEntity> objects = query.list();
        if(objects!= null && !objects.isEmpty()){
            CustomerPackageEntity aObj = objects.get(0);
            return aObj.getPackage_expiry_date();
        }else{
            //check the left half
            query = session.createCriteria(CustomerPackageEntity.class);
            query.add( Restrictions.eq("member_id", memberId));
            query.add(Restrictions.eq("isEnabled",true));
            query.add(Restrictions.ne("id",custPkgEntityId));
            query.add(Restrictions.lt("package_expiry_date", adt));
            query.addOrder(Order.desc("package_expiry_date"));

            query.setFirstResult(0);
            query.setMaxResults(2);
            List<CustomerPackageEntity> objects2 = query.list();

            if(objects2!= null && !objects2.isEmpty()){
                CustomerPackageEntity aObj = objects.get(0);
                return aObj.getPackage_expiry_date();
            }else{
                Calendar c = Calendar.getInstance();
                c.setTime(adt); // Now use today date.
                c.add(Calendar.YEAR, 100);//Add 100 year

                Date date = c.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = simpleDateFormat.format(date);

                dateStr += " 23:59:59";

                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date adt1 = null;
                try {
                    adt1= simpleDateFormat.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    adt1 = new Date();
                }

                return adt1;
            }
        }
    }

    @Override
    public CustomerPackageEntityPaginationObject getCustomerPackageEntityList(long id, int step, int count){

        int pageSize = count;
        Session session = getCurrentSession();

        Criteria count_query = session.createCriteria(CustomerPackageEntity.class);
        Criterion q1 = Restrictions.eq("member_id", id);
        count_query.add(q1);
        count_query.add(Restrictions.eq("isEnabled",true));
        count_query.addOrder(Order.asc("package_expiry_date"));



        //countQuery.add(Restrictions.eq("isEnabled",true));
        count_query.setProjection(Projections.rowCount());
        Long countResults = (Long) count_query.uniqueResult();

        //String countQ = "Select count (p.id) from Package p";
        //Query countQuery = session.createQuery(countQ);
        //Long countResults = (Long) countQuery.uniqueResult();

        if(countResults == 0){
            CustomerPackageEntityPaginationObject obj = new CustomerPackageEntityPaginationObject();
            obj.setTotalCount(countResults);
            obj.setaList(new ArrayList<CustomerPackageEntityPayLoad>());

            return obj;
        }

        int start = -1;

        if((step-1) * count >= countResults){
            System.out.println("#################### Possible attempt to hack in #############");
            return null;
        }

        start = (step-1)*count  ;

        //String hql = "from Package u where ";//where u.username=:unameStr and u.password=:pwdStr";
        Criteria query = session.createCriteria(CustomerPackageEntity.class);
        Criterion q11 = Restrictions.eq("member_id", id);
        query.add(q11);
        query.add(Restrictions.eq("isEnabled",true));
        query.addOrder(Order.asc("package_expiry_date"));

        query.setFirstResult(start);
        query.setMaxResults(count);
        List<CustomerPackageEntity> custPkgEntList = query.list();

        System.out.println("The number of CustomerPackageEntity -- "+custPkgEntList.size() + " --- ");

        //MemberPaginationObject obj = new MemberPaginationObject();
        //obj.setTotalCount(countResults);
        //obj.setPackages(members);

        CustomerPackageEntityPaginationObject obj = new CustomerPackageEntityPaginationObject();
        obj.setTotalCount(countResults);
        List<CustomerPackageEntityPayLoad> arr =  new ArrayList<CustomerPackageEntityPayLoad>();
        for(CustomerPackageEntity aObj : custPkgEntList){

            System.out.println(aObj);
            CustomerPackageEntityPayLoad o1 = new CustomerPackageEntityPayLoad();
            long pkg_id = aObj.getPackage_id();
            Package pkg = getPackageFromId(pkg_id);
            o1.setPkg_name(pkg.getName());
            o1.setId(aObj.getId());
            o1.setEnrollment_date(convertToStrDate(aObj.getPackage_enrollment_date()));
            o1.setFinal_price(aObj.getFinal_price());
            o1.setPackage_expiry_date(convertToStrDate(aObj.getPackage_expiry_date()));

            //package expired calculation
            Date startdt = aObj.getPackage_enrollment_date();
            Date end  = aObj.getPackage_expiry_date();

            long total_days = TimeUnit.DAYS.convert(end.getTime() - startdt.getTime(), TimeUnit.MILLISECONDS);
            long remaining_days = TimeUnit.DAYS.convert(end.getTime() - (new Date()).getTime(), TimeUnit.MILLISECONDS);

            if(remaining_days <= 0  )
                remaining_days = 0;

            System.out.println("Total days -- "+ total_days + " -- remaining days -- "+remaining_days);

            double percentage_elapsed = ((int)total_days - (int)remaining_days )/ (double)total_days * 100;
            System.out.println("Utilization percentage -- "+ percentage_elapsed);
            o1.setPkg_utilization_percentage(new Double(percentage_elapsed).intValue());
            arr.add(o1);

        }
        obj.setaList(arr);

        return obj;

        //return null;
    }

    @Override
    public void saveProgressText(MemberPackageProgressEntity o1){
        Session session = getCurrentSession();
        session.persist(o1);
        System.out.println("MemberPackageProgressEntity added successfully, MemberPackageProgressEntity Details= "+o1);
    }
}
