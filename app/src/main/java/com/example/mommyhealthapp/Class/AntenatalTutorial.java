package com.example.mommyhealthapp.Class;

import java.util.Date;
import java.util.List;

public class AntenatalTutorial {
    private String antenatalCareStatus;
    private Date antenatalCareDate;
    private String antenatalCarePerson;
    private String earlyClinicStatus;
    private Date earlyClinicDate;
    private String earlyClinicPerson;
    private String solveProblemStatus;
    private Date solveProblemDate;
    private String solveProblemPerson;
    private String nutritionStatus;
    private Date nutritionDate;
    private String nutritionPerson;
    private String maternityStatus;
    private Date maternityDate;
    private String maternityPerson;
    private String exerciseStatus;
    private Date exerciseDate;
    private String exercisePerson;
    private String familyPlannerStatus;
    private Date familyPlannerDate;
    private String familyPlannerPerson;
    private String childBirthStatus;
    private Date childBirthDate;
    private String childBirthPerson;
    private String saveBirthStatus;
    private Date saveBirthDate;
    private String saveBirthPerson;
    private String earlyBirthStatus;
    private Date earlyBirthDate;
    private String earlyBirthPerson;
    private String oralHealthStatus;
    private Date oralHealthDate;
    private String oralHealthPerson;

    public AntenatalTutorial(){}

    public AntenatalTutorial(String antenatalCareStatus, Date antenatalCareDate, String antenatalCarePerson, String earlyClinicStatus, Date earlyClinicDate,
                             String earlyClinicPerson, String solveProblemStatus, Date solveProblemDate, String solveProblemPerson, String nutritionStatus,
                             Date nutritionDate, String nutritionPerson, String maternityStatus, Date maternityDate, String maternityPerson, String exerciseStatus,
                             Date exerciseDate, String exercisePerson, String familyPlannerStatus, Date familyPlannerDate, String familyPlannerPerson,
                             String childBirthStatus, Date childBirthDate, String childBirthPerson, String saveBirthStatus, Date saveBirthDate, String saveBirthPerson,
                             String earlyBirthStatus, Date earlyBirthDate, String earlyBirthPerson, String oralHealthStatus, Date oralHealthDate, String oralHealthPerson)
    {
        this.antenatalCareStatus = antenatalCareStatus;
        this.antenatalCareDate = antenatalCareDate;
        this.antenatalCarePerson = antenatalCarePerson;
        this.earlyClinicStatus = earlyClinicStatus;
        this.earlyClinicDate = earlyClinicDate;
        this.earlyClinicPerson = earlyClinicPerson;
        this.solveProblemStatus = solveProblemStatus;
        this.solveProblemDate = solveProblemDate;
        this.solveProblemPerson = solveProblemPerson;
        this.nutritionStatus = nutritionStatus;
        this.nutritionDate = nutritionDate;
        this.nutritionPerson = nutritionPerson;
        this.maternityStatus = maternityStatus;
        this.maternityDate = maternityDate;
        this.maternityPerson = maternityPerson;
        this.exerciseStatus = exerciseStatus;
        this.exerciseDate = exerciseDate;
        this.exercisePerson = exercisePerson;
        this.familyPlannerStatus = familyPlannerStatus;
        this.familyPlannerDate = familyPlannerDate;
        this.familyPlannerPerson = familyPlannerPerson;
        this.childBirthStatus = childBirthStatus;
        this.childBirthDate = childBirthDate;
        this.childBirthPerson = childBirthPerson;
        this.saveBirthStatus = saveBirthStatus;
        this.saveBirthDate = saveBirthDate;
        this.saveBirthPerson = saveBirthPerson;
        this.earlyBirthStatus = earlyBirthStatus;
        this.earlyBirthDate = earlyBirthDate;
        this.earlyBirthPerson = earlyBirthPerson;
        this.oralHealthStatus = oralHealthStatus;
        this.oralHealthDate = oralHealthDate;
        this.oralHealthPerson = oralHealthPerson;
    }

    public String getAntenatalCareStatus() {
        return antenatalCareStatus;
    }

    public void setAntenatalCareStatus(String antenatalCareStatus) {
        this.antenatalCareStatus = antenatalCareStatus;
    }

    public Date getAntenatalCareDate() {
        return antenatalCareDate;
    }

    public void setAntenatalCareDate(Date antenatalCareDate) {
        this.antenatalCareDate = antenatalCareDate;
    }

    public String getAntenatalCarePerson() {
        return antenatalCarePerson;
    }

    public void setAntenatalCarePerson(String antenatalCarePerson) {
        this.antenatalCarePerson = antenatalCarePerson;
    }

    public String getEarlyClinicStatus() {
        return earlyClinicStatus;
    }

    public void setEarlyClinicStatus(String earlyClinicStatus) {
        this.earlyClinicStatus = earlyClinicStatus;
    }

    public Date getEarlyClinicDate() {
        return earlyClinicDate;
    }

    public void setEarlyClinicDate(Date earlyClinicDate) {
        this.earlyClinicDate = earlyClinicDate;
    }

    public String getEarlyClinicPerson() {
        return earlyClinicPerson;
    }

    public void setEarlyClinicPerson(String earlyClinicPerson) {
        this.earlyClinicPerson = earlyClinicPerson;
    }

    public String getSolveProblemStatus() {
        return solveProblemStatus;
    }

    public void setSolveProblemStatus(String solveProblemStatus) {
        this.solveProblemStatus = solveProblemStatus;
    }

    public Date getSolveProblemDate() {
        return solveProblemDate;
    }

    public void setSolveProblemDate(Date solveProblemDate) {
        this.solveProblemDate = solveProblemDate;
    }

    public String getSolveProblemPerson() {
        return solveProblemPerson;
    }

    public void setSolveProblemPerson(String solveProblemPerson) {
        this.solveProblemPerson = solveProblemPerson;
    }

    public String getNutritionStatus() {
        return nutritionStatus;
    }

    public void setNutritionStatus(String nutritionStatus) {
        this.nutritionStatus = nutritionStatus;
    }

    public Date getNutritionDate() {
        return nutritionDate;
    }

    public void setNutritionDate(Date nutritionDate) {
        this.nutritionDate = nutritionDate;
    }

    public String getNutritionPerson() {
        return nutritionPerson;
    }

    public void setNutritionPerson(String nutritionPerson) {
        this.nutritionPerson = nutritionPerson;
    }

    public String getMaternityStatus() {
        return maternityStatus;
    }

    public void setMaternityStatus(String maternityStatus) {
        this.maternityStatus = maternityStatus;
    }

    public Date getMaternityDate() {
        return maternityDate;
    }

    public void setMaternityDate(Date maternityDate) {
        this.maternityDate = maternityDate;
    }

    public String getMaternityPerson() {
        return maternityPerson;
    }

    public void setMaternityPerson(String maternityPerson) {
        this.maternityPerson = maternityPerson;
    }

    public String getExerciseStatus() {
        return exerciseStatus;
    }

    public void setExerciseStatus(String exerciseStatus) {
        this.exerciseStatus = exerciseStatus;
    }

    public Date getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(Date exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    public String getExercisePerson() {
        return exercisePerson;
    }

    public void setExercisePerson(String exercisePerson) {
        this.exercisePerson = exercisePerson;
    }

    public String getFamilyPlannerStatus() {
        return familyPlannerStatus;
    }

    public void setFamilyPlannerStatus(String familyPlannerStatus) {
        this.familyPlannerStatus = familyPlannerStatus;
    }

    public Date getFamilyPlannerDate() {
        return familyPlannerDate;
    }

    public void setFamilyPlannerDate(Date familyPlannerDate) {
        this.familyPlannerDate = familyPlannerDate;
    }

    public String getFamilyPlannerPerson() {
        return familyPlannerPerson;
    }

    public void setFamilyPlannerPerson(String familyPlannerPerson) {
        this.familyPlannerPerson = familyPlannerPerson;
    }

    public String getChildBirthStatus() {
        return childBirthStatus;
    }

    public void setChildBirthStatus(String childBirthStatus) {
        this.childBirthStatus = childBirthStatus;
    }

    public Date getChildBirthDate() {
        return childBirthDate;
    }

    public void setChildBirthDate(Date childBirthDate) {
        this.childBirthDate = childBirthDate;
    }

    public String getChildBirthPerson() {
        return childBirthPerson;
    }

    public void setChildBirthPerson(String childBirthPerson) {
        this.childBirthPerson = childBirthPerson;
    }

    public String getSaveBirthStatus() {
        return saveBirthStatus;
    }

    public void setSaveBirthStatus(String saveBirthStatus) {
        this.saveBirthStatus = saveBirthStatus;
    }

    public Date getSaveBirthDate() {
        return saveBirthDate;
    }

    public void setSaveBirthDate(Date saveBirthDate) {
        this.saveBirthDate = saveBirthDate;
    }

    public String getSaveBirthPerson() {
        return saveBirthPerson;
    }

    public void setSaveBirthPerson(String saveBirthPerson) {
        this.saveBirthPerson = saveBirthPerson;
    }

    public String getEarlyBirthStatus() {
        return earlyBirthStatus;
    }

    public void setEarlyBirthStatus(String earlyBirthStatus) {
        this.earlyBirthStatus = earlyBirthStatus;
    }

    public Date getEarlyBirthDate() {
        return earlyBirthDate;
    }

    public void setEarlyBirthDate(Date earlyBirthDate) {
        this.earlyBirthDate = earlyBirthDate;
    }

    public String getEarlyBirthPerson() {
        return earlyBirthPerson;
    }

    public void setEarlyBirthPerson(String earlyBirthPerson) {
        this.earlyBirthPerson = earlyBirthPerson;
    }

    public String getOralHealthStatus() {
        return oralHealthStatus;
    }

    public void setOralHealthStatus(String oralHealthStatus) {
        this.oralHealthStatus = oralHealthStatus;
    }

    public Date getOralHealthDate() {
        return oralHealthDate;
    }

    public void setOralHealthDate(Date oralHealthDate) {
        this.oralHealthDate = oralHealthDate;
    }

    public String getOralHealthPerson() {
        return oralHealthPerson;
    }

    public void setOralHealthPerson(String oralHealthPerson) {
        this.oralHealthPerson = oralHealthPerson;
    }
}
