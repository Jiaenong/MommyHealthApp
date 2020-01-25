package com.example.mommyhealthapp.Class;

import java.util.Date;

public class CurrentHealthStatus {
    private Double currentHeight;
    private Double currentBMI;
    private String currentThyroid;
    private String currentJaundice;
    private String currentPallor;
    private String currentLeftBreast;
    private String currentRightBreast;
    private String currentLeftVein;
    private String currentRightVein;
    private String currentAbdomen;
    private String currentOthers;
    private String medicalPersonnelId;
    private Date createdDate;

    public CurrentHealthStatus(){}

    public CurrentHealthStatus(Double currentHeight, Double currentBMI, String currentThyroid, String currentJaundice, String currentPallor,
                               String currentLeftBreast, String currentRightBreast, String currentLeftVein, String currentRightVein, String currentAbdomen, String currentOthers,
                               String medicalPersonnelId, Date createdDate)
    {
        this.currentHeight = currentHeight;
        this.currentBMI = currentBMI;
        this.currentThyroid = currentThyroid;
        this. currentJaundice = currentJaundice;
        this.currentPallor = currentPallor;
        this.currentLeftBreast = currentLeftBreast;
        this.currentRightBreast = currentRightBreast;
        this.currentLeftVein = currentLeftVein;
        this.currentRightVein = currentRightVein;
        this.currentAbdomen = currentAbdomen;
        this.currentOthers = currentOthers;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public Double getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(Double currentHeight) {
        this.currentHeight = currentHeight;
    }

    public Double getCurrentBMI() {
        return currentBMI;
    }

    public void setCurrentBMI(Double currentBMI) {
        this.currentBMI = currentBMI;
    }

    public String getCurrentThyroid() {
        return currentThyroid;
    }

    public void setCurrentThyroid(String currentThyroid) {
        this.currentThyroid = currentThyroid;
    }

    public String getCurrentJaundice() {
        return currentJaundice;
    }

    public void setCurrentJaundice(String currentJaundice) {
        this.currentJaundice = currentJaundice;
    }

    public String getCurrentPallor() {
        return currentPallor;
    }

    public void setCurrentPallor(String currentPallor) {
        this.currentPallor = currentPallor;
    }

    public String getCurrentLeftBreast() {
        return currentLeftBreast;
    }

    public void setCurrentLeftBreast(String currentLeftBreast) {
        this.currentLeftBreast = currentLeftBreast;
    }

    public String getCurrentRightBreast() {
        return currentRightBreast;
    }

    public void setCurrentRightBreast(String currentRightBreast) {
        this.currentRightBreast = currentRightBreast;
    }

    public String getCurrentLeftVein() {
        return currentLeftVein;
    }

    public void setCurrentLeftVein(String currentLeftVein) {
        this.currentLeftVein = currentLeftVein;
    }

    public String getCurrentRightVein() {
        return currentRightVein;
    }

    public void setCurrentRightVein(String currentRightVein) {
        this.currentRightVein = currentRightVein;
    }

    public String getCurrentAbdomen() {
        return currentAbdomen;
    }

    public void setCurrentAbdomen(String currentAbdomen) {
        this.currentAbdomen = currentAbdomen;
    }

    public String getCurrentOthers() {
        return currentOthers;
    }

    public void setCurrentOthers(String currentOthers) {
        this.currentOthers = currentOthers;
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
