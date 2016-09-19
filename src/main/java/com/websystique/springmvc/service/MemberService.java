package com.websystique.springmvc.service;

import com.websystique.springmvc.model.*;
import com.websystique.springmvc.payload.CustomerPackageEntityPaginationObject;
import com.websystique.springmvc.payload.MemberFilter;
import com.websystique.springmvc.payload.PackageFilter;

import java.util.Date;

/**
 * Created by arkadutta on 06/09/16.
 */
public interface MemberService {

    public MemberPaginationObject getMember(int step, MemberFilter filter, int count);

    public MemberPaginationObject getMember(int step, int count,int timeExpiry);

    public void saveMember(Member mem) ;

    public void editMember(Member mem) ;

    public void editAddress(Address addr) ;

    public Member getMemberFromId(long id);

    public int getMemberPackageUtilizationPercentage(long id);

    public CustomerPackageEntityPaginationObject getCustomerPackageEntityList(long id,int step,int count);

    public CustomerPackageEntity getCustomerPackageEntityFromId(long id);

    public Date getExpiryDateAssociatedWithMember(long memberId,long custPkgEntityId);

    public void editCustomerPackageEntity(CustomerPackageEntity mem);

    public void saveCustomerPackageEntity(CustomerPackageEntity obj) ;

    public void saveProgressText(MemberPackageProgressEntity o1);

}
