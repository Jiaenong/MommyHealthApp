package com.example.mommyhealthapp.Class;

import java.util.Date;
import java.util.List;

public class HealthHistory {
    private String menstruationDays;
    private String menstruationRound;
    private String birthControl;
    private String birthControlMethod;
    private String birthControlPeriod;
    private String MotherSmoke;
    private String FatherSmoke;
    private List<String> motherDisease;
    private List<String> familyDisease;
    private Date dos1, dos2, dosAddOn;
    private String medicalPersonnelId;
    private Date createdDate;

    public HealthHistory(){}

    public HealthHistory(String menstruationDays, String menstruationRound, String birthControl, String birthControlMethod, String birthControlPeriod,
                         String MotherSmoke, String FatherSmoke, List<String> motherDisease, List<String> familyDisease, Date dos1, Date dos2, Date dosAddOn,
                         String medicalPersonnelId, Date createdDate)
    {
        this.menstruationDays = menstruationDays;
        this.menstruationRound = menstruationRound;
        this.birthControl = birthControl;
        this.birthControlMethod = birthControlMethod;
        this.birthControlPeriod = birthControlPeriod;
        this.MotherSmoke = MotherSmoke;
        this.FatherSmoke = FatherSmoke;
        this.motherDisease = motherDisease;
        this.familyDisease = familyDisease;
        this.dos1 = dos1;
        this.dos2 = dos2;
        this.dosAddOn = dosAddOn;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
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

    public String getMenstruationDays() {
        return menstruationDays;
    }

    public void setMenstruationDays(String menstruationDays) {
        this.menstruationDays = menstruationDays;
    }

    public String getMenstruationRound() {
        return menstruationRound;
    }

    public void setMenstruationRound(String menstruationRound) {
        this.menstruationRound = menstruationRound;
    }

    public String getBirthControl() {
        return birthControl;
    }

    public void setBirthControl(String birthControl) {
        this.birthControl = birthControl;
    }

    public String getBirthControlMethod() {
        return birthControlMethod;
    }

    public void setBirthControlMethod(String birthControlMethod) {
        this.birthControlMethod = birthControlMethod;
    }

    public String getBirthControlPeriod() {
        return birthControlPeriod;
    }

    public void setBirthControlPeriod(String birthControlPeriod) {
        this.birthControlPeriod = birthControlPeriod;
    }

    public String getMotherSmoke() {
        return MotherSmoke;
    }

    public void setMotherSmoke(String motherSmoke) {
        MotherSmoke = motherSmoke;
    }

    public String getFatherSmoke() {
        return FatherSmoke;
    }

    public void setFatherSmoke(String fatherSmoke) {
        FatherSmoke = fatherSmoke;
    }

    public List<String> getMotherDisease() {
        return motherDisease;
    }

    public void setMotherDisease(List<String> motherDisease) {
        this.motherDisease = motherDisease;
    }

    public List<String> getFamilyDisease() {
        return familyDisease;
    }

    public void setFamilyDisease(List<String> familyDisease) {
        this.familyDisease = familyDisease;
    }

    public Date getDos1() {
        return dos1;
    }

    public void setDos1(Date dos1) {
        this.dos1 = dos1;
    }

    public Date getDos2() {
        return dos2;
    }

    public void setDos2(Date dos2) {
        this.dos2 = dos2;
    }

    public Date getDosAddOn() {
        return dosAddOn;
    }

    public void setDosAddOn(Date dosAddOn) {
        this.dosAddOn = dosAddOn;
    }
}
