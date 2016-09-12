package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Member;
import com.websystique.springmvc.model.MemberPaginationObject;
import com.websystique.springmvc.model.Package;
import com.websystique.springmvc.model.PackagePaginationObject;
import com.websystique.springmvc.payload.MemberFilter;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        String countQ = "Select count (p.id) from Member p";
        Query countQuery = session.createQuery(countQ);
        Long countResults = (Long) countQuery.uniqueResult();

        int start = -1;

        if((step-1) * count >= countResults){
            System.out.println("#################### Possible attempt to hack in #############");
            return null;
        }

        start = (step-1)*count  ;

        String nameStr = filter.getNameStr();
        String mobileStr = filter.getMobileStr();

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
        obj.setTotalCount(members.size());
        obj.setPackages(members);

        return obj;
    }
}
