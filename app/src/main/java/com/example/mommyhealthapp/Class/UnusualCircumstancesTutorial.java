package com.example.mommyhealthapp.Class;

import java.util.Date;

public class UnusualCircumstancesTutorial {
    private String eclampsiaStatus;
    private Date eclmapsiaDate;
    private String eclampsiaPerson;
    private String diabetesStatus;
    private Date diabetesDate;
    private String diabetesPerson;
    private String anemiaStatus;
    private Date anemiaDate;
    private String anemiaPerson;
    private String bleedingStatus;
    private Date bleedingDate;
    private String bleedingPerson;

    public UnusualCircumstancesTutorial(){}

    public UnusualCircumstancesTutorial(String eclampsiaStatus, Date eclmapsiaDate, String eclampsiaPerson, String diabetesStatus, Date diabetesDate, String diabetesPerson,
                                        String anemiaStatus, Date anemiaDate, String anemiaPerson, String bleedingStatus, Date bleedingDate, String bleedingPerson)
    {
        this.eclampsiaStatus = eclampsiaStatus;
        this.eclmapsiaDate = eclmapsiaDate;
        this.eclampsiaPerson = eclampsiaPerson;
        this.diabetesStatus = diabetesStatus;
        this.diabetesDate = diabetesDate;
        this.diabetesPerson = diabetesPerson;
        this.anemiaStatus = anemiaStatus;
        this.anemiaDate = anemiaDate;
        this.anemiaPerson = anemiaPerson;
        this.bleedingStatus = bleedingStatus;
        this.bleedingDate = bleedingDate;
        this.bleedingPerson = bleedingPerson;
    }

    public String getEclampsiaStatus() {
        return eclampsiaStatus;
    }

    public void setEclampsiaStatus(String eclampsiaStatus) {
        this.eclampsiaStatus = eclampsiaStatus;
    }

    public Date getEclmapsiaDate() {
        return eclmapsiaDate;
    }

    public void setEclmapsiaDate(Date eclmapsiaDate) {
        this.eclmapsiaDate = eclmapsiaDate;
    }

    public String getEclampsiaPerson() {
        return eclampsiaPerson;
    }

    public void setEclampsiaPerson(String eclampsiaPerson) {
        this.eclampsiaPerson = eclampsiaPerson;
    }

    public String getDiabetesStatus() {
        return diabetesStatus;
    }

    public void setDiabetesStatus(String diabetesStatus) {
        this.diabetesStatus = diabetesStatus;
    }

    public Date getDiabetesDate() {
        return diabetesDate;
    }

    public void setDiabetesDate(Date diabetesDate) {
        this.diabetesDate = diabetesDate;
    }

    public String getDiabetesPerson() {
        return diabetesPerson;
    }

    public void setDiabetesPerson(String diabetesPerson) {
        this.diabetesPerson = diabetesPerson;
    }

    public String getAnemiaStatus() {
        return anemiaStatus;
    }

    public void setAnemiaStatus(String anemiaStatus) {
        this.anemiaStatus = anemiaStatus;
    }

    public Date getAnemiaDate() {
        return anemiaDate;
    }

    public void setAnemiaDate(Date anemiaDate) {
        this.anemiaDate = anemiaDate;
    }

    public String getAnemiaPerson() {
        return anemiaPerson;
    }

    public void setAnemiaPerson(String anemiaPerson) {
        this.anemiaPerson = anemiaPerson;
    }

    public String getBleedingStatus() {
        return bleedingStatus;
    }

    public void setBleedingStatus(String bleedingStatus) {
        this.bleedingStatus = bleedingStatus;
    }

    public Date getBleedingDate() {
        return bleedingDate;
    }

    public void setBleedingDate(Date bleedingDate) {
        this.bleedingDate = bleedingDate;
    }

    public String getBleedingPerson() {
        return bleedingPerson;
    }

    public void setBleedingPerson(String bleedingPerson) {
        this.bleedingPerson = bleedingPerson;
    }
}
