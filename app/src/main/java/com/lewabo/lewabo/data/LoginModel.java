package com.lewabo.lewabo.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("expireTime")
    @Expose
    private String expireTime;
    @SerializedName("subPlan")
    @Expose
    private SubPlan subPlan;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private String status;

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public SubPlan getSubPlan() {
        return subPlan;
    }

    public void setSubPlan(SubPlan subPlan) {
        this.subPlan = subPlan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "expireTime='" + expireTime + '\'' +
                ", subPlan=" + subPlan +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}