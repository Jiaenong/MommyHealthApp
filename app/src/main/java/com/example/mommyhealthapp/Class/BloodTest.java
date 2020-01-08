package com.example.mommyhealthapp.Class;

import java.util.Date;

public class BloodTest {
    private String bloodGroup;
    private String rhd;
    private String rpr;
    private String medicalPersonnelId;
    private Date createdDate;

    public BloodTest(){}

    public BloodTest(String bloodGroup, String rhd, String rpr, String medicalPersonnelId, Date createdDate)
    {
        this.bloodGroup = bloodGroup;
        this.rhd = rhd;
        this.rpr = rpr;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getRhd() {
        return rhd;
    }

    public void setRhd(String rhd) {
        this.rhd = rhd;
    }

    public String getRpr() {
        return rpr;
    }

    public void setRpr(String rpr) {
        this.rpr = rpr;
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
