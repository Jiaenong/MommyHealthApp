package com.example.mommyhealthapp.Class;

import java.util.Date;

public class PreviousPregnant {
    private String year;
    private String pregnantResult;
    private String birthType;
    private String placeGiveBirth;
    private String gender;
    private double birthWeight;
    private String complicationMother;
    private String complicationChild;
    private String breastFeedingPeriod;
    private String childSituation;
    private Date today;
    private String createdBy;

    public PreviousPregnant(){}

    public PreviousPregnant(String year, String pregnantResult, String birthType, String placeGiveBirth, String gender, double birthWeight, String complicationMother,
                            String complicationChild, String breastFeedingPeriod, String childSituation, Date today, String createdBy)
    {
        this.year = year;
        this.pregnantResult = pregnantResult;
        this.birthType = birthType;
        this.placeGiveBirth = placeGiveBirth;
        this.gender = gender;
        this.birthWeight = birthWeight;
        this.complicationMother = complicationMother;
        this.complicationChild = complicationChild;
        this.breastFeedingPeriod = breastFeedingPeriod;
        this.childSituation = childSituation;
        this.today = today;
        this.createdBy = createdBy;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPregnantResult() {
        return pregnantResult;
    }

    public void setPregnantResult(String pregnantResult) {
        this.pregnantResult = pregnantResult;
    }

    public String getBirthType() {
        return birthType;
    }

    public void setBirthType(String birthType) {
        this.birthType = birthType;
    }

    public String getPlaceGiveBirth() {
        return placeGiveBirth;
    }

    public void setPlaceGiveBirth(String placeGiveBirth) {
        this.placeGiveBirth = placeGiveBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getBirthWeight() {
        return birthWeight;
    }

    public void setBirthWeight(double birthWeight) {
        this.birthWeight = birthWeight;
    }

    public String getComplicationMother() {
        return complicationMother;
    }

    public void setComplicationMother(String complicationMother) {
        this.complicationMother = complicationMother;
    }

    public String getComplicationChild() {
        return complicationChild;
    }

    public void setComplicationChild(String complicationChild) {
        this.complicationChild = complicationChild;
    }

    public String getBreastFeedingPeriod() {
        return breastFeedingPeriod;
    }

    public void setBreastFeedingPeriod(String breastFeedingPeriod) {
        this.breastFeedingPeriod = breastFeedingPeriod;
    }

    public String getChildSituation() {
        return childSituation;
    }

    public void setChildSituation(String childSituation) {
        this.childSituation = childSituation;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
