package com.example.mommyhealthapp.Class;

import java.util.Date;

public class MentalHealth {
    private float ratingHardToWindDown;
    private float ratingDrynessMouth;
    private float ratingNotExperiencePositive;
    private float ratingBreathDifficult;
    private float ratingNoInitiative;
    private float ratingOverReact;
    private float ratingExperienceTrembling;
    private float ratingNervousEnergy;
    private float ratingWorriedSituation;
    private float ratingNothingToLookForward;
    private float ratingAgigate;
    private float ratingDifficultRelax;
    private float ratingDownHearted;
    private float ratingIntolerant;
    private float ratingPanic;
    private float ratingLackEnthusiastic;
    private float ratingFeelNotWorth;
    private float ratingTouchy;
    private float ratingSenseHeartRateIncrease;
    private float ratingScared;
    private float ratingLifeMeaningLess;
    private String Stress;
    private String Anxiety;
    private String Depression;
    private String medicalPersonnelId;
    private Date createdDate;

    public MentalHealth(){}

    public MentalHealth(float ratingHardToWindDown, float ratingDrynessMouth, float ratingNotExperiencePositive, float ratingBreathDifficult, float ratingNoInitiative, float ratingOverReact,
                        float ratingExperienceTrembling, float ratingNervousEnergy, float ratingWorriedSituation, float ratingNothingToLookForward, float ratingAgigate, float ratingDifficultRelax,
                        float ratingDownHearted, float ratingIntolerant, float ratingPanic, float ratingLackEnthusiastic, float ratingFeelNotWorth, float ratingTouchy, float ratingSenseHeartRateIncrease,
                        float ratingScared, float ratingLifeMeaningLess, String Stress, String Anxiety, String Depression, String medicalPersonnelId, Date createdDate)
    {
        this.ratingHardToWindDown = ratingHardToWindDown;
        this.ratingDrynessMouth = ratingDrynessMouth;
        this.ratingNotExperiencePositive = ratingNotExperiencePositive;
        this.ratingBreathDifficult = ratingBreathDifficult;
        this.ratingNoInitiative = ratingNoInitiative;
        this.ratingOverReact = ratingOverReact;
        this.ratingExperienceTrembling = ratingExperienceTrembling;
        this.ratingNervousEnergy = ratingNervousEnergy;
        this.ratingWorriedSituation = ratingWorriedSituation;
        this.ratingNothingToLookForward = ratingNothingToLookForward;
        this.ratingAgigate = ratingAgigate;
        this.ratingDifficultRelax = ratingDifficultRelax;
        this.ratingDownHearted = ratingDownHearted;
        this.ratingIntolerant = ratingIntolerant;
        this.ratingPanic = ratingPanic;
        this.ratingLackEnthusiastic = ratingLackEnthusiastic;
        this.ratingFeelNotWorth = ratingFeelNotWorth;
        this.ratingTouchy = ratingTouchy;
        this.ratingSenseHeartRateIncrease = ratingSenseHeartRateIncrease;
        this.ratingScared = ratingScared;
        this.ratingLifeMeaningLess = ratingLifeMeaningLess;
        this.Stress = Stress;
        this.Anxiety = Anxiety;
        this.Depression = Depression;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public String getStress() {
        return Stress;
    }

    public void setStress(String stress) {
        Stress = stress;
    }

    public String getAnxiety() {
        return Anxiety;
    }

    public void setAnxiety(String anxiety) {
        Anxiety = anxiety;
    }

    public String getDepression() {
        return Depression;
    }

    public void setDepression(String depression) {
        Depression = depression;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public float getRatingHardToWindDown() {
        return ratingHardToWindDown;
    }

    public void setRatingHardToWindDown(float ratingHardToWindDown) {
        this.ratingHardToWindDown = ratingHardToWindDown;
    }

    public float getRatingDrynessMouth() {
        return ratingDrynessMouth;
    }

    public void setRatingDrynessMouth(float ratingDrynessMouth) {
        this.ratingDrynessMouth = ratingDrynessMouth;
    }

    public float getRatingNotExperiencePositive() {
        return ratingNotExperiencePositive;
    }

    public void setRatingNotExperiencePositive(float ratingNotExperiencePositive) {
        this.ratingNotExperiencePositive = ratingNotExperiencePositive;
    }

    public float getRatingBreathDifficult() {
        return ratingBreathDifficult;
    }

    public void setRatingBreathDifficult(float ratingBreathDifficult) {
        this.ratingBreathDifficult = ratingBreathDifficult;
    }

    public float getRatingNoInitiative() {
        return ratingNoInitiative;
    }

    public void setRatingNoInitiative(float ratingNoInitiative) {
        this.ratingNoInitiative = ratingNoInitiative;
    }

    public float getRatingOverReact() {
        return ratingOverReact;
    }

    public void setRatingOverReact(float ratingOverReact) {
        this.ratingOverReact = ratingOverReact;
    }

    public float getRatingExperienceTrembling() {
        return ratingExperienceTrembling;
    }

    public void setRatingExperienceTrembling(float ratingExperienceTrembling) {
        this.ratingExperienceTrembling = ratingExperienceTrembling;
    }

    public float getRatingNervousEnergy() {
        return ratingNervousEnergy;
    }

    public void setRatingNervousEnergy(float ratingNervousEnergy) {
        this.ratingNervousEnergy = ratingNervousEnergy;
    }

    public float getRatingWorriedSituation() {
        return ratingWorriedSituation;
    }

    public void setRatingWorriedSituation(float ratingWorriedSituation) {
        this.ratingWorriedSituation = ratingWorriedSituation;
    }

    public float getRatingNothingToLookForward() {
        return ratingNothingToLookForward;
    }

    public void setRatingNothingToLookForward(float ratingNothingToLookForward) {
        this.ratingNothingToLookForward = ratingNothingToLookForward;
    }

    public float getRatingAgigate() {
        return ratingAgigate;
    }

    public void setRatingAgigate(float ratingAgigate) {
        this.ratingAgigate = ratingAgigate;
    }

    public float getRatingDifficultRelax() {
        return ratingDifficultRelax;
    }

    public void setRatingDifficultRelax(float ratingDifficultRelax) {
        this.ratingDifficultRelax = ratingDifficultRelax;
    }

    public float getRatingDownHearted() {
        return ratingDownHearted;
    }

    public void setRatingDownHearted(float ratingDownHearted) {
        this.ratingDownHearted = ratingDownHearted;
    }

    public float getRatingIntolerant() {
        return ratingIntolerant;
    }

    public void setRatingIntolerant(float ratingIntolerant) {
        this.ratingIntolerant = ratingIntolerant;
    }

    public float getRatingPanic() {
        return ratingPanic;
    }

    public void setRatingPanic(float ratingPanic) {
        this.ratingPanic = ratingPanic;
    }

    public float getRatingLackEnthusiastic() {
        return ratingLackEnthusiastic;
    }

    public void setRatingLackEnthusiastic(float ratingLackEnthusiastic) {
        this.ratingLackEnthusiastic = ratingLackEnthusiastic;
    }

    public float getRatingFeelNotWorth() {
        return ratingFeelNotWorth;
    }

    public void setRatingFeelNotWorth(float ratingFeelNotWorth) {
        this.ratingFeelNotWorth = ratingFeelNotWorth;
    }

    public float getRatingTouchy() {
        return ratingTouchy;
    }

    public void setRatingTouchy(float ratingTouchy) {
        this.ratingTouchy = ratingTouchy;
    }

    public float getRatingSenseHeartRateIncrease() {
        return ratingSenseHeartRateIncrease;
    }

    public void setRatingSenseHeartRateIncrease(float ratingSenseHeartRateIncrease) {
        this.ratingSenseHeartRateIncrease = ratingSenseHeartRateIncrease;
    }

    public float getRatingScared() {
        return ratingScared;
    }

    public void setRatingScared(float ratingScared) {
        this.ratingScared = ratingScared;
    }

    public float getRatingLifeMeaningLess() {
        return ratingLifeMeaningLess;
    }

    public void setRatingLifeMeaningLess(float ratingLifeMeaningLess) {
        this.ratingLifeMeaningLess = ratingLifeMeaningLess;
    }

    public String getMedicalPersonnelId() {
        return medicalPersonnelId;
    }

    public void setMedicalPersonnelId(String medicalPersonnelId) {
        this.medicalPersonnelId = medicalPersonnelId;
    }
}
