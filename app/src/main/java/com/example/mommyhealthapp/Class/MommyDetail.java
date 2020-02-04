package com.example.mommyhealthapp.Class;

import java.sql.Time;
import java.util.Date;

public class MommyDetail {
    private double height;
    private double weight;
    private String familyDisease;
    private Date lnmp;
    private Date edd;
    private Date edp;
    private Date today;
    private String marriageStatus;
    private String husbandName;
    private String husbandIC;
    private String husbandWork;
    private String husbandWorkAddress;
    private String husbandPhone;

    public MommyDetail(){}

    public MommyDetail (double height, double weight, String familyDisease, Date lnmp, Date edd, Date edp, String marriageStatus,
                        String husbandName, String husbandIC, String husbandWork, String husbandWorkAddress, String husbandPhone, Date today)
    {
        this.height = height;
        this.weight = weight;
        this.familyDisease = familyDisease;
        this.lnmp = lnmp;
        this.edd = edd;
        this.edp = edp;
        this.marriageStatus = marriageStatus;
        this.husbandName = husbandName;
        this.husbandIC = husbandIC;
        this.husbandWork = husbandWork;
        this.husbandWorkAddress = husbandWorkAddress;
        this.husbandPhone = husbandPhone;
        this.today = today;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFamilyDisease() {
        return familyDisease;
    }

    public void setFamilyDisease(String familyDisease) {
        this.familyDisease = familyDisease;
    }

    public Date getLnmp() {
        return lnmp;
    }

    public void setLnmp(Date lnmp) {
        this.lnmp = lnmp;
    }

    public Date getEdd() {
        return edd;
    }

    public void setEdd(Date edd) {
        this.edd = edd;
    }

    public Date getEdp() {
        return edp;
    }

    public void setEdp(Date edp) {
        this.edp = edp;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getHusbandIC() {
        return husbandIC;
    }

    public void setHusbandIC(String husbandIC) {
        this.husbandIC = husbandIC;
    }

    public String getHusbandWork() {
        return husbandWork;
    }

    public void setHusbandWork(String husbandWork) {
        this.husbandWork = husbandWork;
    }

    public String getHusbandWorkAddress() {
        return husbandWorkAddress;
    }

    public void setHusbandWorkAddress(String husbandWorkAddress) {
        this.husbandWorkAddress = husbandWorkAddress;
    }

    public String getHusbandPhone() {
        return husbandPhone;
    }

    public void setHusbandPhone(String husbandPhone) {
        this.husbandPhone = husbandPhone;
    }
}