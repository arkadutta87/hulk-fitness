package com.websystique.springmvc.trial;

import com.websystique.springmvc.utils.RedisUtility;

/**
 * Created by arkadutta on 18/08/16.
 */
public class RedisTest {

    public static void main(String[] args){
        String key = "name";
        String value = "Arka Dutta";

        RedisUtility.getInstance().setKeyValTTL(key,value,10);

        System.out.println("Key - " + key + " - Value - "+value);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The value inserted - "+ RedisUtility.getInstance().getVal(key));
        RedisUtility.getInstance().shutdown();
    }
}
