package com.websystique.springmvc.utils;

import com.websystique.springmvc.model.Member;
import com.websystique.springmvc.model.Package;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserPayLoad;
import com.websystique.springmvc.payload.MemberPayLoad;
import com.websystique.springmvc.payload.PackageCRUDResponse;
import com.websystique.springmvc.payload.PackagePayLoad;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by arkadutta on 15/08/16.
 */
public class DataConversion {

    public static void copyUserToPayLoad(User aUser , UserPayLoad pLoad){
        pLoad.setId(aUser.getId());
        pLoad.setUsername(aUser.getUsername());
        pLoad.setAddress(aUser.getAddress());
        pLoad.setEmail(aUser.getEmail());
    }

    public static void copyPayLoadToUser(UserPayLoad aUser , User pLoad){
        pLoad.setId(aUser.getId());
        pLoad.setUsername(aUser.getUsername());
        pLoad.setAddress(aUser.getAddress());
        pLoad.setEmail(aUser.getUsername());
    }

    public static List<UserPayLoad> convertUserToPayload(List<User> users){

        if(users== null)
            return null;
        else{
            List<UserPayLoad> payLoad = new ArrayList<UserPayLoad>();
            for(User usr : users){
                UserPayLoad pLoad = new UserPayLoad();
                copyUserToPayLoad(usr,pLoad);
                payLoad.add(pLoad);
            }

            return payLoad;
        }
    }

    //member related method //need to correct it
    private static void copyMemberToMemberPayLoad(Member mem , MemberPayLoad pl){
        pl.setId(mem.getId());
        pl.setFirstName(mem.getFirstName());
        pl.setLastName(mem.getLastName());
        pl.setMobileNumber(mem.getMobile());

        //date formatter
        Date dt = mem.getLatest_pkg_expiry();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(dt);
        pl.setExpiration_date(strDate);

        //find out number of days left for expiry of a particular package
        long diff = dt.getTime() - (new Date()).getTime();
        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        System.out.println ("Difference of Days: " + daysDiff);

        int day = new Long(daysDiff).intValue();

        pl.setExpiration_time_left_in_days(day);
    }

    public static List<PackagePayLoad> convertPackageToPackagePayLoad(List<Package> packages){

        if(packages== null)
            return null;
        else{
            List<PackagePayLoad> payLoad = new ArrayList<PackagePayLoad>();
            for(Package pck : packages){
                PackagePayLoad pLoad = new PackagePayLoad();
                copyPackageToPackagePayLoad(pck,pLoad);
                payLoad.add(pLoad);
            }

            return payLoad;
        }
    }

    private static void copyPackageToPackagePayLoad(Package pkg , PackagePayLoad pl){
        pl.setId(pkg.getId());
        pl.setName(pkg.getName());
        pl.setSpecialName(pkg.getSpecialName());
        pl.setTrial(pkg.isTrial());

        //duration text
        DecimalFormat df = new DecimalFormat("#.00");
        String duration = df.format(pkg.getDurationValue()) + " " + pkg.getDurationUnit().toLowerCase();
        pl.setDuration(duration);

        //price formatter
        double money = pkg.getPrice();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);
        System.out.println(moneyString);
        pl.setPrice(moneyString);

        //date formatter
        Date dt = pkg.getUpdatedOn();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(dt);
        pl.setUpdatedOn(strDate);


    }

    //member related method
    public static List<MemberPayLoad> convertMemberToMemberPayLoad(List<Member> members){

        if(members== null)
            return null;
        else{
            List<MemberPayLoad> payLoad = new ArrayList<MemberPayLoad>();
            for(Member mem : members){
                MemberPayLoad pLoad = new MemberPayLoad();
                copyMemberToMemberPayLoad(mem,pLoad);//need to correct it
                payLoad.add(pLoad);
            }

            return payLoad;
        }
    }

    public static void convertPkgModelToPackageCRUDRes(Package pkg, PackageCRUDResponse res){
        if(pkg!= null){
            res.setId(pkg.getId());
            res.setTrial(pkg.isTrial());
            res.setSpecialName(pkg.getSpecialName());
            res.setName(pkg.getName());
            res.setDurationUnit(pkg.getDurationUnit());
            res.setPrice(pkg.getPrice());
            res.setDurationValue(pkg.getDurationValue());
            res.setPackageDetails(pkg.getPackageDetails());
        }
    }
}
