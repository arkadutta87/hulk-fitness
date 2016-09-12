package com.websystique.springmvc.common;

/**
 * Created by arkadutta on 01/09/16.
 */
public enum PackageDurationUnitEnum {

    MONTHS("MONTHS"),
    DAYS("DAYS");

    public String value;

    private PackageDurationUnitEnum(String value){
        this.value = value;
    }
}
