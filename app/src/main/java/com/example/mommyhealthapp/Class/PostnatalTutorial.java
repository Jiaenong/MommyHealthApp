package com.example.mommyhealthapp.Class;

import java.util.Date;

public class PostnatalTutorial {
    private String mealNutritionStatus;
    private Date mealNutritionDate;
    private String mealNutritionPerson;
    private String postnatalExerciseStatus;
    private Date postnatalExerciseDate;
    private String postnatalExercisePerson;
    private String hygieneStatus;
    private Date hygieneDate;
    private String hygienePerson;
    private String earlyTreatmentStatus;
    private Date earlyTreatmentDate;
    private String earlyTreatmentPerson;

    public PostnatalTutorial(){}

    public PostnatalTutorial(String mealNutritionStatus, Date mealNutritionDate, String mealNutritionPerson, String postnatalExerciseStatus, Date postnatalExerciseDate,
                             String postnatalExercisePerson, String hygieneStatus, Date hygieneDate, String hygienePerson, String earlyTreatmentStatus,
                             Date earlyTreatmentDate, String earlyTreatmentPerson)
    {
        this.mealNutritionStatus = mealNutritionStatus;
        this.mealNutritionDate = mealNutritionDate;
        this.mealNutritionPerson = mealNutritionPerson;
        this.postnatalExerciseStatus = postnatalExerciseStatus;
        this.postnatalExerciseDate = postnatalExerciseDate;
        this.postnatalExercisePerson = postnatalExercisePerson;
        this.hygieneStatus = hygieneStatus;
        this.hygieneDate = hygieneDate;
        this.hygienePerson = hygienePerson;
        this.earlyTreatmentStatus = earlyTreatmentStatus;
        this.earlyTreatmentDate = earlyTreatmentDate;
        this.earlyTreatmentPerson = earlyTreatmentPerson;
    }

    public String getMealNutritionStatus() {
        return mealNutritionStatus;
    }

    public void setMealNutritionStatus(String mealNutritionStatus) {
        this.mealNutritionStatus = mealNutritionStatus;
    }

    public Date getMealNutritionDate() {
        return mealNutritionDate;
    }

    public void setMealNutritionDate(Date mealNutritionDate) {
        this.mealNutritionDate = mealNutritionDate;
    }

    public String getMealNutritionPerson() {
        return mealNutritionPerson;
    }

    public void setMealNutritionPerson(String mealNutritionPerson) {
        this.mealNutritionPerson = mealNutritionPerson;
    }

    public String getPostnatalExerciseStatus() {
        return postnatalExerciseStatus;
    }

    public void setPostnatalExerciseStatus(String postnatalExerciseStatus) {
        this.postnatalExerciseStatus = postnatalExerciseStatus;
    }

    public Date getPostnatalExerciseDate() {
        return postnatalExerciseDate;
    }

    public void setPostnatalExerciseDate(Date postnatalExerciseDate) {
        this.postnatalExerciseDate = postnatalExerciseDate;
    }

    public String getPostnatalExercisePerson() {
        return postnatalExercisePerson;
    }

    public void setPostnatalExercisePerson(String postnatalExercisePerson) {
        this.postnatalExercisePerson = postnatalExercisePerson;
    }

    public String getHygieneStatus() {
        return hygieneStatus;
    }

    public void setHygieneStatus(String hygieneStatus) {
        this.hygieneStatus = hygieneStatus;
    }

    public Date getHygieneDate() {
        return hygieneDate;
    }

    public void setHygieneDate(Date hygieneDate) {
        this.hygieneDate = hygieneDate;
    }

    public String getHygienePerson() {
        return hygienePerson;
    }

    public void setHygienePerson(String hygienePerson) {
        this.hygienePerson = hygienePerson;
    }

    public String getEarlyTreatmentStatus() {
        return earlyTreatmentStatus;
    }

    public void setEarlyTreatmentStatus(String earlyTreatmentStatus) {
        this.earlyTreatmentStatus = earlyTreatmentStatus;
    }

    public Date getEarlyTreatmentDate() {
        return earlyTreatmentDate;
    }

    public void setEarlyTreatmentDate(Date earlyTreatmentDate) {
        this.earlyTreatmentDate = earlyTreatmentDate;
    }

    public String getEarlyTreatmentPerson() {
        return earlyTreatmentPerson;
    }

    public void setEarlyTreatmentPerson(String earlyTreatmentPerson) {
        this.earlyTreatmentPerson = earlyTreatmentPerson;
    }
}
