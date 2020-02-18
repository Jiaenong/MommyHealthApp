package com.example.mommyhealthapp.Class;

import java.util.Date;

public class MGTT {
    private boolean ageAbove35;
    private boolean BMI;
    private boolean gestationalDiabetes;
    private boolean InstrauterineDeath;
    private boolean stillBirth;
    private boolean neotanalDeath;
    private boolean recurrentMiscarriage;
    private boolean congenitalAbnormality;
    private boolean mascrosomicBaby;
    private boolean firstDegreeRelativeDiabetes;
    private boolean glycosuria;
    private boolean essentialHypertension;
    private boolean pregnancyInducedHypertension;
    private boolean polyhydramanios;
    private boolean uterusLargerThanDate;
    private boolean multiplePregnancy;
    private boolean recurrentUTI;
    private String medicalPersonnelId;
    private Date createdDate;

    public MGTT(){}

    public MGTT(boolean ageAbove35, boolean BMI, boolean gestationalDiabetes, boolean instrauterineDeath, boolean stillBirth, boolean neotanalDeath,
                boolean recurrentMiscarriage, boolean congenitalAbnormality, boolean mascrosomicBaby, boolean firstDegreeRelativeDiabetes, boolean glycosuria,
                boolean essentialHypertension, boolean pregnancyInducedHypertension, boolean polyhydramanios, boolean uterusLargerThanDate, boolean multiplePregnancy,
                boolean recurrentUTI, String medicalPersonnelId, Date createdDate)
    {
        this.ageAbove35 = ageAbove35;
        this.BMI = BMI;
        this.gestationalDiabetes = gestationalDiabetes;
        InstrauterineDeath = instrauterineDeath;
        this.stillBirth = stillBirth;
        this.neotanalDeath = neotanalDeath;
        this.recurrentMiscarriage = recurrentMiscarriage;
        this.congenitalAbnormality = congenitalAbnormality;
        this.mascrosomicBaby = mascrosomicBaby;
        this.firstDegreeRelativeDiabetes = firstDegreeRelativeDiabetes;
        this.glycosuria = glycosuria;
        this.essentialHypertension = essentialHypertension;
        this.pregnancyInducedHypertension = pregnancyInducedHypertension;
        this.polyhydramanios = polyhydramanios;
        this.uterusLargerThanDate = uterusLargerThanDate;
        this.multiplePregnancy = multiplePregnancy;
        this.recurrentUTI = recurrentUTI;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public boolean isAgeAbove35() {
        return ageAbove35;
    }

    public void setAgeAbove35(boolean ageAbove35) {
        this.ageAbove35 = ageAbove35;
    }

    public boolean isBMI() {
        return BMI;
    }

    public void setBMI(boolean BMI) {
        this.BMI = BMI;
    }

    public boolean isGestationalDiabetes() {
        return gestationalDiabetes;
    }

    public void setGestationalDiabetes(boolean gestationalDiabetes) {
        this.gestationalDiabetes = gestationalDiabetes;
    }

    public boolean isInstrauterineDeath() {
        return InstrauterineDeath;
    }

    public void setInstrauterineDeath(boolean instrauterineDeath) {
        InstrauterineDeath = instrauterineDeath;
    }

    public boolean isStillBirth() {
        return stillBirth;
    }

    public void setStillBirth(boolean stillBirth) {
        this.stillBirth = stillBirth;
    }

    public boolean isNeotanalDeath() {
        return neotanalDeath;
    }

    public void setNeotanalDeath(boolean neotanalDeath) {
        this.neotanalDeath = neotanalDeath;
    }

    public boolean isRecurrentMiscarriage() {
        return recurrentMiscarriage;
    }

    public void setRecurrentMiscarriage(boolean recurrentMiscarriage) {
        this.recurrentMiscarriage = recurrentMiscarriage;
    }

    public boolean isCongenitalAbnormality() {
        return congenitalAbnormality;
    }

    public void setCongenitalAbnormality(boolean congenitalAbnormality) {
        this.congenitalAbnormality = congenitalAbnormality;
    }

    public boolean isMascrosomicBaby() {
        return mascrosomicBaby;
    }

    public void setMascrosomicBaby(boolean mascrosomicBaby) {
        this.mascrosomicBaby = mascrosomicBaby;
    }

    public boolean isFirstDegreeRelativeDiabetes() {
        return firstDegreeRelativeDiabetes;
    }

    public void setFirstDegreeRelativeDiabetes(boolean firstDegreeRelativeDiabetes) {
        this.firstDegreeRelativeDiabetes = firstDegreeRelativeDiabetes;
    }

    public boolean isGlycosuria() {
        return glycosuria;
    }

    public void setGlycosuria(boolean glycosuria) {
        this.glycosuria = glycosuria;
    }

    public boolean isEssentialHypertension() {
        return essentialHypertension;
    }

    public void setEssentialHypertension(boolean essentialHypertension) {
        this.essentialHypertension = essentialHypertension;
    }

    public boolean isPregnancyInducedHypertension() {
        return pregnancyInducedHypertension;
    }

    public void setPregnancyInducedHypertension(boolean pregnancyInducedHypertension) {
        this.pregnancyInducedHypertension = pregnancyInducedHypertension;
    }

    public boolean isPolyhydramanios() {
        return polyhydramanios;
    }

    public void setPolyhydramanios(boolean polyhydramanios) {
        this.polyhydramanios = polyhydramanios;
    }

    public boolean isUterusLargerThanDate() {
        return uterusLargerThanDate;
    }

    public void setUterusLargerThanDate(boolean uterusLargerThanDate) {
        this.uterusLargerThanDate = uterusLargerThanDate;
    }

    public boolean isMultiplePregnancy() {
        return multiplePregnancy;
    }

    public void setMultiplePregnancy(boolean multiplePregnancy) {
        this.multiplePregnancy = multiplePregnancy;
    }

    public boolean isRecurrentUTI() {
        return recurrentUTI;
    }

    public void setRecurrentUTI(boolean recurrentUTI) {
        this.recurrentUTI = recurrentUTI;
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
