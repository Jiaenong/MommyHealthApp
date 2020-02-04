package com.example.mommyhealthapp.Class;

import java.util.Date;

public class AppointmentDate {
    private Date appointmentDate;
    private Date appointmentTime;
    private String mommyId;
    private String medicalPersonnelId;
    private Date createdDate;

    public AppointmentDate(){}

    public AppointmentDate(Date appointmentDate, Date appointmentTime, String mommyId, String medicalPersonnelId, Date createdDate)
    {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.mommyId = mommyId;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getMommyId() {
        return mommyId;
    }

    public void setMommyId(String mommyId) {
        this.mommyId = mommyId;
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
