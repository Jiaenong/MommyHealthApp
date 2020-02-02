package com.example.mommyhealthapp.Class;

import java.util.Date;

public class BreastExamine {
    private int numOfChildren;
    private int menarcheAge;
    private int monopauseAge;
    private String breastCancelHistory;
    private String firstDegreeBreastCancel;
    private String previousBreastSurgery;
    private String hormoneReplacementTherapy;
    private String hormoneContraceptive;
    private String breastFed;
    private String previousMMG;
    private String lump;
    private String nippleDischarge;
    private String nippleRetraction;
    private String discomfort;
    private String axillaryNodesSwelling;
    private String clinicalInterpretation;
    private String medicalPersonnelId;
    private Date createdDate;

    public BreastExamine(){}

    public BreastExamine(int numOfChildren, int menarcheAge, int monopauseAge, String breastCancelHistory, String firstDegreeBreastCancel, String previousBreastSurgery,
                         String hormoneReplacementTherapy, String hormoneContraceptive, String breastFed, String previousMMG, String lump, String nippleDischarge, String nippleRetraction,
                         String discomfort, String axillaryNodesSwelling, String clinicalInterpretation, String medicalPersonnelId, Date createdDate)
    {
        this.numOfChildren = numOfChildren;
        this.menarcheAge = menarcheAge;
        this.monopauseAge = monopauseAge;
        this.breastCancelHistory = breastCancelHistory;
        this.firstDegreeBreastCancel = firstDegreeBreastCancel;
        this.previousBreastSurgery = previousBreastSurgery;
        this.hormoneReplacementTherapy = hormoneReplacementTherapy;
        this.hormoneContraceptive = hormoneContraceptive;
        this.breastFed = breastFed;
        this.previousMMG = previousMMG;
        this.lump = lump;
        this.nippleDischarge = nippleDischarge;
        this.nippleRetraction = nippleRetraction;
        this.discomfort = discomfort;
        this.axillaryNodesSwelling = axillaryNodesSwelling;
        this.clinicalInterpretation = clinicalInterpretation;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public int getMenarcheAge() {
        return menarcheAge;
    }

    public void setMenarcheAge(int menarcheAge) {
        this.menarcheAge = menarcheAge;
    }

    public int getMonopauseAge() {
        return monopauseAge;
    }

    public void setMonopauseAge(int monopauseAge) {
        this.monopauseAge = monopauseAge;
    }

    public String getBreastCancelHistory() {
        return breastCancelHistory;
    }

    public void setBreastCancelHistory(String breastCancelHistory) {
        this.breastCancelHistory = breastCancelHistory;
    }

    public String getFirstDegreeBreastCancel() {
        return firstDegreeBreastCancel;
    }

    public void setFirstDegreeBreastCancel(String firstDegreeBreastCancel) {
        this.firstDegreeBreastCancel = firstDegreeBreastCancel;
    }

    public String getPreviousBreastSurgery() {
        return previousBreastSurgery;
    }

    public void setPreviousBreastSurgery(String previousBreastSurgery) {
        this.previousBreastSurgery = previousBreastSurgery;
    }

    public String getHormoneReplacementTherapy() {
        return hormoneReplacementTherapy;
    }

    public void setHormoneReplacementTherapy(String hormoneReplacementTherapy) {
        this.hormoneReplacementTherapy = hormoneReplacementTherapy;
    }

    public String getHormoneContraceptive() {
        return hormoneContraceptive;
    }

    public void setHormoneContraceptive(String hormoneContraceptive) {
        this.hormoneContraceptive = hormoneContraceptive;
    }

    public String getBreastFed() {
        return breastFed;
    }

    public void setBreastFed(String breastFed) {
        this.breastFed = breastFed;
    }

    public String getPreviousMMG() {
        return previousMMG;
    }

    public void setPreviousMMG(String previousMMG) {
        this.previousMMG = previousMMG;
    }

    public String getLump() {
        return lump;
    }

    public void setLump(String lump) {
        this.lump = lump;
    }

    public String getNippleDischarge() {
        return nippleDischarge;
    }

    public void setNippleDischarge(String nippleDischarge) {
        this.nippleDischarge = nippleDischarge;
    }

    public String getNippleRetraction() {
        return nippleRetraction;
    }

    public void setNippleRetraction(String nippleRetraction) {
        this.nippleRetraction = nippleRetraction;
    }

    public String getDiscomfort() {
        return discomfort;
    }

    public void setDiscomfort(String discomfort) {
        this.discomfort = discomfort;
    }

    public String getAxillaryNodesSwelling() {
        return axillaryNodesSwelling;
    }

    public void setAxillaryNodesSwelling(String axillaryNodesSwelling) {
        this.axillaryNodesSwelling = axillaryNodesSwelling;
    }

    public String getClinicalInterpretation() {
        return clinicalInterpretation;
    }

    public void setClinicalInterpretation(String clinicalInterpretation) {
        this.clinicalInterpretation = clinicalInterpretation;
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
