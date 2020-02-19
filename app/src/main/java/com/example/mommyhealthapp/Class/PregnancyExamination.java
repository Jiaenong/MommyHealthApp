package com.example.mommyhealthapp.Class;

import java.util.Date;
import java.util.List;

public class PregnancyExamination {
    private double bookingWeight;
    private double bookingBMI;
    private double bookingBP;
    private String lkkr;
    private Date nextAppointmentDate;
    private Date getNextAppointmentTime;
    private double urineAlb;
    private double urineSugar;
    private double hb;
    private double bodyWeight;
    private double bloodPressure;
    private double pulse;
    private String edema;
    private int pregnancyWeek;
    private double uterineHeight;
    private String presentPosition;
    private String heart;
    private String motion;
    private List<ProblemPE> peProblem;
    private String medicalPersonnelName;
    private Date createdDate;
    private String pregnancyExamId;

    public PregnancyExamination (){}

    public PregnancyExamination(double bookingWeight, double bookingBMI, double bookingBP, String lkkr, Date nextAppointmentDate, Date getNextAppointmentTime,
                                double urineAlb, double urineSugar, double hb, double bodyWeight, double bloodPressure, double pulse, String edema, int pregnancyWeek,
                                double uterineHeight, String presentPosition, String heart, String motion, List<ProblemPE> peProblem, String medicalPersonnelName,
                                Date createdDate, String pregnancyExamId)
    {
        this.bookingWeight = bookingWeight;
        this.bookingBMI = bookingBMI;
        this.bookingBP = bookingBP;
        this.lkkr = lkkr;
        this.nextAppointmentDate = nextAppointmentDate;
        this.getNextAppointmentTime = getNextAppointmentTime;
        this.urineAlb = urineAlb;
        this.urineSugar = urineSugar;
        this.hb = hb;
        this.bodyWeight = bodyWeight;
        this.bloodPressure = bloodPressure;
        this.pulse = pulse;
        this.edema = edema;
        this.pregnancyWeek = pregnancyWeek;
        this.uterineHeight = uterineHeight;
        this.presentPosition = presentPosition;
        this.heart = heart;
        this.motion = motion;
        this.peProblem = peProblem;
        this.medicalPersonnelName = medicalPersonnelName;
        this.createdDate = createdDate;
        this.pregnancyExamId = pregnancyExamId;
    }

    public double getBookingWeight() {
        return bookingWeight;
    }

    public void setBookingWeight(double bookingWeight) {
        this.bookingWeight = bookingWeight;
    }

    public double getBookingBMI() {
        return bookingBMI;
    }

    public void setBookingBMI(double bookingBMI) {
        this.bookingBMI = bookingBMI;
    }

    public double getBookingBP() {
        return bookingBP;
    }

    public void setBookingBP(double bookingBP) {
        this.bookingBP = bookingBP;
    }

    public String getLkkr() {
        return lkkr;
    }

    public void setLkkr(String lkkr) {
        this.lkkr = lkkr;
    }

    public Date getNextAppointmentDate() {
        return nextAppointmentDate;
    }

    public void setNextAppointmentDate(Date nextAppointmentDate) {
        this.nextAppointmentDate = nextAppointmentDate;
    }

    public Date getGetNextAppointmentTime() {
        return getNextAppointmentTime;
    }

    public void setGetNextAppointmentTime(Date getNextAppointmentTime) {
        this.getNextAppointmentTime = getNextAppointmentTime;
    }

    public double getUrineAlb() {
        return urineAlb;
    }

    public void setUrineAlb(double urineAlb) {
        this.urineAlb = urineAlb;
    }

    public double getUrineSugar() {
        return urineSugar;
    }

    public void setUrineSugar(double urineSugar) {
        this.urineSugar = urineSugar;
    }

    public double getHb() {
        return hb;
    }

    public void setHb(double hb) {
        this.hb = hb;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public double getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(double bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public double getPulse() {
        return pulse;
    }

    public void setPulse(double pulse) {
        this.pulse = pulse;
    }

    public String getEdema() {
        return edema;
    }

    public void setEdema(String edema) {
        this.edema = edema;
    }

    public int getPregnancyWeek() {
        return pregnancyWeek;
    }

    public void setPregnancyWeek(int pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
    }

    public double getUterineHeight() {
        return uterineHeight;
    }

    public void setUterineHeight(double uterineHeight) {
        this.uterineHeight = uterineHeight;
    }

    public String getPresentPosition() {
        return presentPosition;
    }

    public void setPresentPosition(String presentPosition) {
        this.presentPosition = presentPosition;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public List<ProblemPE> getPeProblem() {
        return peProblem;
    }

    public void setPeProblem(List<ProblemPE> peProblem) {
        this.peProblem = peProblem;
    }

    public String getMedicalPersonnelName() {
        return medicalPersonnelName;
    }

    public void setMedicalPersonnelName(String medicalPersonnelName) {
        this.medicalPersonnelName = medicalPersonnelName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPregnancyExamId() {
        return pregnancyExamId;
    }

    public void setPregnancyExamId(String pregnancyExamId) {
        this.pregnancyExamId = pregnancyExamId;
    }
}
