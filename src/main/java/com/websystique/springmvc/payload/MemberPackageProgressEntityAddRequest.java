package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 19/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPackageProgressEntityAddRequest {

    private long package_mem_entity_id;
    private String text;

    public MemberPackageProgressEntityAddRequest(){

    }

    public long getPackage_mem_entity_id() {
        return package_mem_entity_id;
    }

    public void setPackage_mem_entity_id(long package_mem_entity_id) {
        this.package_mem_entity_id = package_mem_entity_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MemberPackageProgressEntityAddRequest{" +
                "package_mem_entity_id=" + package_mem_entity_id +
                ", text='" + text + '\'' +
                '}';
    }
}
