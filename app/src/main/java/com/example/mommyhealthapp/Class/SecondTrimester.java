package com.example.mommyhealthapp.Class;

import java.util.Date;
import java.util.List;

public class SecondTrimester {
    private List<String> redCode;
    private List<String> yellowCode;
    private List<String> greenCode;
    private List<String> whiteCode;
    private String medicalPersonnelId;
    private Date createdDate;

    public SecondTrimester() {
    }

    public SecondTrimester(List<String> redCode, List<String> yellowCode, List<String> greenCode, List<String> whiteCode, String medicalPersonnelId, Date createdDate) {
        this.redCode = redCode;
        this.yellowCode = yellowCode;
        this.greenCode = greenCode;
        this.whiteCode = whiteCode;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public List<String> getRedCode() {
        return redCode;
    }

    public void setRedCode(List<String> redCode) {
        this.redCode = redCode;
    }

    public List<String> getYellowCode() {
        return yellowCode;
    }

    public void setYellowCode(List<String> yellowCode) {
        this.yellowCode = yellowCode;
    }

    public List<String> getGreenCode() {
        return greenCode;
    }

    public void setGreenCode(List<String> greenCode) {
        this.greenCode = greenCode;
    }

    public List<String> getWhiteCode() {
        return whiteCode;
    }

    public void setWhiteCode(List<String> whiteCode) {
        this.whiteCode = whiteCode;
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
