package com.example.mommyhealthapp.Class;

import java.util.Date;

public class OtherTutorial {
    private String caringNewBornStatus;
    private Date caringNewBornDate;
    private String caringNewBornPerson;
    private String neonatalJaundiceStatus;
    private Date neonatalJaundiceDate;
    private String neonatalJaundicePerson;
    private String papSmearStatus;
    private Date papSmearDate;
    private String papSmearPerson;
    private String breastCancerScreeningStatus;
    private Date breastCancerScreeningDate;
    private String breastCancerScreeningPerson;

    public OtherTutorial(){}

    public OtherTutorial(String caringNewBornStatus, Date caringNewBornDate, String caringNewBornPerson, String neonatalJaundiceStatus, Date neonatalJaundiceDate,
                         String neonatalJaundicePerson, String papSmearStatus, Date papSmearDate, String papSmearPerson, String breastCancerScreeningStatus,
                         Date breastCancerScreeningDate, String breastCancerScreeningPerson)
    {
        this.caringNewBornStatus = caringNewBornStatus;
        this.caringNewBornDate = caringNewBornDate;
        this.caringNewBornPerson = caringNewBornPerson;
        this.neonatalJaundiceStatus = neonatalJaundiceStatus;
        this.neonatalJaundiceDate = neonatalJaundiceDate;
        this.neonatalJaundicePerson = neonatalJaundicePerson;
        this.papSmearStatus = papSmearStatus;
        this.papSmearDate = papSmearDate;
        this.papSmearPerson = papSmearPerson;
        this.breastCancerScreeningStatus = breastCancerScreeningStatus;
        this.breastCancerScreeningDate = breastCancerScreeningDate;
        this.breastCancerScreeningPerson = breastCancerScreeningPerson;
    }

    public String getCaringNewBornStatus() {
        return caringNewBornStatus;
    }

    public void setCaringNewBornStatus(String caringNewBornStatus) {
        this.caringNewBornStatus = caringNewBornStatus;
    }

    public Date getCaringNewBornDate() {
        return caringNewBornDate;
    }

    public void setCaringNewBornDate(Date caringNewBornDate) {
        this.caringNewBornDate = caringNewBornDate;
    }

    public String getCaringNewBornPerson() {
        return caringNewBornPerson;
    }

    public void setCaringNewBornPerson(String caringNewBornPerson) {
        this.caringNewBornPerson = caringNewBornPerson;
    }

    public String getNeonatalJaundiceStatus() {
        return neonatalJaundiceStatus;
    }

    public void setNeonatalJaundiceStatus(String neonatalJaundiceStatus) {
        this.neonatalJaundiceStatus = neonatalJaundiceStatus;
    }

    public Date getNeonatalJaundiceDate() {
        return neonatalJaundiceDate;
    }

    public void setNeonatalJaundiceDate(Date neonatalJaundiceDate) {
        this.neonatalJaundiceDate = neonatalJaundiceDate;
    }

    public String getNeonatalJaundicePerson() {
        return neonatalJaundicePerson;
    }

    public void setNeonatalJaundicePerson(String neonatalJaundicePerson) {
        this.neonatalJaundicePerson = neonatalJaundicePerson;
    }

    public String getPapSmearStatus() {
        return papSmearStatus;
    }

    public void setPapSmearStatus(String papSmearStatus) {
        this.papSmearStatus = papSmearStatus;
    }

    public Date getPapSmearDate() {
        return papSmearDate;
    }

    public void setPapSmearDate(Date papSmearDate) {
        this.papSmearDate = papSmearDate;
    }

    public String getPapSmearPerson() {
        return papSmearPerson;
    }

    public void setPapSmearPerson(String papSmearPerson) {
        this.papSmearPerson = papSmearPerson;
    }

    public String getBreastCancerScreeningStatus() {
        return breastCancerScreeningStatus;
    }

    public void setBreastCancerScreeningStatus(String breastCancerScreeningStatus) {
        this.breastCancerScreeningStatus = breastCancerScreeningStatus;
    }

    public Date getBreastCancerScreeningDate() {
        return breastCancerScreeningDate;
    }

    public void setBreastCancerScreeningDate(Date breastCancerScreeningDate) {
        this.breastCancerScreeningDate = breastCancerScreeningDate;
    }

    public String getBreastCancerScreeningPerson() {
        return breastCancerScreeningPerson;
    }

    public void setBreastCancerScreeningPerson(String breastCancerScreeningPerson) {
        this.breastCancerScreeningPerson = breastCancerScreeningPerson;
    }
}
