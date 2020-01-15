package com.example.mommyhealthapp.Class;

import java.util.Date;

public class ScreenTest {
    private String rhesusBloodGroup;
    private String tpha;
    private String rapidTest;
    private String hepatitisB;
    private String thalassaemia;
    private String bfmp;
    private Date createdDate;
    private String medicalPersonnelId;

    public ScreenTest(){}

    public ScreenTest(String rhesusBloodGroup, String tpha, String rapidTest, String hepatitisB, String thalassaemia, String bfmp, Date createdDate, String medicalPersonnelId)
    {
        this.rhesusBloodGroup = rhesusBloodGroup;
        this.tpha = tpha;
        this.rapidTest = rapidTest;
        this.hepatitisB = hepatitisB;
        this.thalassaemia = thalassaemia;
        this.createdDate = createdDate;
        this.medicalPersonnelId = medicalPersonnelId;
        this.bfmp = bfmp;
    }

    public String getBfmp() {
        return bfmp;
    }

    public void setBfmp(String bfmp) {
        this.bfmp = bfmp;
    }

    public String getRhesusBloodGroup() {
        return rhesusBloodGroup;
    }

    public void setRhesusBloodGroup(String rhesusBloodGroup) {
        this.rhesusBloodGroup = rhesusBloodGroup;
    }

    public String getTpha() {
        return tpha;
    }

    public void setTpha(String tpha) {
        this.tpha = tpha;
    }

    public String getRapidTest() {
        return rapidTest;
    }

    public void setRapidTest(String rapidTest) {
        this.rapidTest = rapidTest;
    }

    public String getHepatitisB() {
        return hepatitisB;
    }

    public void setHepatitisB(String hepatitisB) {
        this.hepatitisB = hepatitisB;
    }

    public String getThalassaemia() {
        return thalassaemia;
    }

    public void setThalassaemia(String thalassaemia) {
        this.thalassaemia = thalassaemia;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getMedicalPersonnelId() {
        return medicalPersonnelId;
    }

    public void setMedicalPersonnelId(String medicalPersonnelId) {
        this.medicalPersonnelId = medicalPersonnelId;
    }
}
