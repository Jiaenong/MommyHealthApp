package com.example.mommyhealthapp.Class;

import java.util.Date;

public class MGTTResult {
    private Date firstTrimesterDate;
    private String firstPOA;
    private String firstBloodSugar;
    private String firstPostprandial;
    private Date SecTrimesterDate;
    private String SecPOA;
    private String SecBloodSugar;
    private String SecPostprandial;
    private Date thirdTrimesterDate;
    private String thirdPOA;
    private String thirdBloodSugar;
    private String thirdPostprandial;
    private String medicalPersonnelId;
    private Date createdDate;

    public MGTTResult(){}

    public MGTTResult(Date firstTrimesterDate, String firstPOA, String firstBloodSugar, String firstPostprandial, Date secTrimesterDate, String secPOA, String secBloodSugar,
                      String secPostprandial, Date thirdTrimesterDate, String thirdPOA, String thirdBloodSugar, String thirdPostprandial, String medicalPersonnelId,
                      Date createdDate)
    {
        this.firstTrimesterDate = firstTrimesterDate;
        this.firstPOA = firstPOA;
        this.firstBloodSugar = firstBloodSugar;
        this.firstPostprandial = firstPostprandial;
        this.SecTrimesterDate = secTrimesterDate;
        this.SecPOA = secPOA;
        this.SecBloodSugar = secBloodSugar;
        this.SecPostprandial = secPostprandial;
        this.thirdTrimesterDate = thirdTrimesterDate;
        this.thirdPOA = thirdPOA;
        this.thirdBloodSugar = thirdBloodSugar;
        this.thirdPostprandial = thirdPostprandial;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public Date getFirstTrimesterDate() {
        return firstTrimesterDate;
    }

    public void setFirstTrimesterDate(Date firstTrimesterDate) {
        this.firstTrimesterDate = firstTrimesterDate;
    }

    public String getFirstPOA() {
        return firstPOA;
    }

    public void setFirstPOA(String firstPOA) {
        this.firstPOA = firstPOA;
    }

    public String getFirstBloodSugar() {
        return firstBloodSugar;
    }

    public void setFirstBloodSugar(String firstBloodSugar) {
        this.firstBloodSugar = firstBloodSugar;
    }

    public String getFirstPostprandial() {
        return firstPostprandial;
    }

    public void setFirstPostprandial(String firstPostprandial) {
        this.firstPostprandial = firstPostprandial;
    }

    public Date getSecTrimesterDate() {
        return SecTrimesterDate;
    }

    public void setSecTrimesterDate(Date secTrimesterDate) {
        SecTrimesterDate = secTrimesterDate;
    }

    public String getSecPOA() {
        return SecPOA;
    }

    public void setSecPOA(String secPOA) {
        SecPOA = secPOA;
    }

    public String getSecBloodSugar() {
        return SecBloodSugar;
    }

    public void setSecBloodSugar(String secBloodSugar) {
        SecBloodSugar = secBloodSugar;
    }

    public String getSecPostprandial() {
        return SecPostprandial;
    }

    public void setSecPostprandial(String secPostprandial) {
        SecPostprandial = secPostprandial;
    }

    public Date getThirdTrimesterDate() {
        return thirdTrimesterDate;
    }

    public void setThirdTrimesterDate(Date thirdTrimesterDate) {
        this.thirdTrimesterDate = thirdTrimesterDate;
    }

    public String getThirdPOA() {
        return thirdPOA;
    }

    public void setThirdPOA(String thirdPOA) {
        this.thirdPOA = thirdPOA;
    }

    public String getThirdBloodSugar() {
        return thirdBloodSugar;
    }

    public void setThirdBloodSugar(String thirdBloodSugar) {
        this.thirdBloodSugar = thirdBloodSugar;
    }

    public String getThirdPostprandial() {
        return thirdPostprandial;
    }

    public void setThirdPostprandial(String thirdPostprandial) {
        this.thirdPostprandial = thirdPostprandial;
    }

    public String getMedicalPersonnelId() {
        return medicalPersonnelId;
    }

    public void setMedicalPersonnelId(String medicalPersonnelId) {
        this.medicalPersonnelId = medicalPersonnelId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
