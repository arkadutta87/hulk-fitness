package com.websystique.springmvc.service;

import com.websystique.springmvc.model.*;
import com.websystique.springmvc.model.Package;
import com.websystique.springmvc.payload.PackageFilter;
import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadutta on 01/09/16.
 */
@Service("packageService")
@Transactional
public class PackageServiceImpl implements PackageService {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Package getPackageFromId(long id){
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

    private void copyPkgToPkg(Package source, Package destination){
        destination.setName(source.getName());
        destination.setSpecialName(source.getSpecialName());
        destination.setEnabled(source.isEnabled());
        destination.setDurationUnit(source.getDurationUnit());
        destination.setPackageDetails(source.getPackageDetails());
        destination.setDurationValue(source.getDurationValue());
        destination.setPrice(source.getPrice());
        destination.setTrial(source.isTrial());

        //destination.setCreatedOn(source); // never update created on time
        destination.setUpdatedOn(source.getUpdatedOn());
        destination.setUpdatedBy(source.getUpdatedBy());
    }

    @Override
    public void updatePackage(Package pkg){
        Session session = getCurrentSession();
        Package pkgCurrent = getPackageFromId(pkg.getId());

        if (pkgCurrent==null) {
            System.out.println("Package with id " + pkg.getId() + " not found");
        }

        copyPkgToPkg(pkg,pkgCurrent);

        session.update(pkgCurrent);
        System.out.println("Update call - "+pkgCurrent);
    }

    @Override
    public void savePackage(Package pkg) {
        Session session = getCurrentSession();
        session.persist(pkg);
        System.out.println("Package added successfully, Package Details="+pkg);

        /*SQLQuery query = session.createSQLQuery("Select * from Employee");
        query.setFirstResult(0);
        query.setMaxResults(10);*/
    }

    @Override
    public PackagePaginationObject getPackage(int step, PackageFilter filter, int count) {

        int pageSize = count;
        Session session = this.sessionFactory.getCurrentSession();

        String searchStr = filter.getSearchStr();
        String unit = filter.getDuration() != null ? filter.getDuration().getUnit() : "";
        double value = filter.getDuration() != null ? filter.getDuration().getValue() : -1;

        Criteria crit = session.createCriteria(Package.class);
        if (searchStr != null && !(searchStr.trim().isEmpty())) {

            Criterion q1 = Restrictions.ilike("name", searchStr, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.ilike("specialName", searchStr, MatchMode.ANYWHERE);
            crit.add(Restrictions.or(q1, q2));
        }

        if(unit != null && !(unit.trim().isEmpty()) && value!= -1){
            Criterion q1 = Restrictions.ilike("durationUnit", unit, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.eq("durationValue",value);

            crit.add(Restrictions.and(q1, q2));

        }
        crit.add(Restrictions.eq("isEnabled",true));
        crit.setProjection(Projections.rowCount());
        Long countResults = (Long) crit.uniqueResult();

        //String countQ = "Select count (p.id) from Package p";
        //Query countQuery = session.createQuery(countQ);
        //Long countResults = (Long) countQuery.uniqueResult();

        if(countResults == 0){
            PackagePaginationObject obj = new PackagePaginationObject();
            obj.setTotalCount(countResults);
            obj.setPackages(new ArrayList<Package>());

            return obj;
        }

        int start = -1;

        if((step-1) * count >= countResults){
            System.out.println("#################### Possible attempt to hack in #############");
            return null;
        }

        start = (step-1)*count  ;

        //String hql = "from Package u where ";//where u.username=:unameStr and u.password=:pwdStr";
        Criteria query = session.createCriteria(Package.class);
        if (searchStr != null && !(searchStr.trim().isEmpty())) {

            //hql += "u.name like ? or u.specialName like ? ";
            Criterion q1 = Restrictions.ilike("name", searchStr, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.ilike("specialName", searchStr, MatchMode.ANYWHERE);
            query.add(Restrictions.or(q1, q2));
            //query.add();
            /*
            Criteria criteria = getSession().createCriteria(clazz);
Criterion rest1= Restrictions.and(Restrictions.eq(A, "X"),
           Restrictions.in("B", Arrays.asList("X",Y)));
Criterion rest2= Restrictions.and(Restrictions.eq(A, "Y"),
           Restrictions.eq(B, "Z"));
criteria.add(Restrictions.or(rest1, rest2));
             */
            //hql.setString(0, "%"+searchField+"%");

        }

        if(unit != null && !(unit.trim().isEmpty()) && value!= -1){
            Criterion q1 = Restrictions.ilike("durationUnit", unit, MatchMode.ANYWHERE);
            Criterion q2 = Restrictions.eq("durationValue",value);

            query.add(Restrictions.and(q1, q2));

        }
        //int lastPageNumber = (int) ((countResults / pageSize) + 1);

        //Query selectQuery = session.createQuery("From Foo");

        //sort based on updation_date
        query.add(Restrictions.eq("isEnabled",true));//always show enabled packages only.
        query.addOrder(Order.desc("updatedOn"));
        query.setFirstResult(start);
        query.setMaxResults(count);
        List<Package> packages = query.list();

        System.out.println("The number of packages -- "+packages.size() + " --- ");

        PackagePaginationObject obj = new PackagePaginationObject();
        obj.setTotalCount(countResults);
        obj.setPackages(packages);

        return obj;
    }
}
