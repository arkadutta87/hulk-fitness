package com.websystique.springmvc.service;

import com.websystique.springmvc.model.MemberPaginationObject;
import com.websystique.springmvc.payload.MemberFilter;
import com.websystique.springmvc.payload.PackageFilter;

/**
 * Created by arkadutta on 06/09/16.
 */
public interface MemberService {

    public MemberPaginationObject getMember(int step, MemberFilter filter, int count);


}
