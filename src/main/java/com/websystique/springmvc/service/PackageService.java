package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Package;
import com.websystique.springmvc.model.PackagePaginationObject;
import com.websystique.springmvc.payload.PackageFilter;

import java.util.List;

/**
 * Created by arkadutta on 01/09/16.
 */
public interface PackageService {

    public PackagePaginationObject getPackage(int step, PackageFilter filter, int count);

    public Package getPackageFromId(long id);

    public void updatePackage(Package pkg);

    public void savePackage(Package pkg);
}
