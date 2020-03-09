package com.example.mommyhealthapp.Class;

import java.util.Date;

public class Notification {
    private String notificationDetail;
    private Date notificationDate;
    private String medicalPersonnelId;
    private Date createdDate;
    private String notificationStatus;
    private String mummyDocId;

    public Notification(){}

    public Notification(String notificationDetail, Date notificationDate, String medicalPersonnelId, Date createdDate, String notificationStatus, String mummyDocId) {
        this.notificationDetail = notificationDetail;
        this.notificationDate = notificationDate;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
        this.notificationStatus = notificationStatus;
        this.mummyDocId = mummyDocId;
    }

    public String getNotificationDetail() {
        return notificationDetail;
    }

    public void setNotificationDetail(String notificationDetail) {
        this.notificationDetail = notificationDetail;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
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

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getMummyDocId() {
        return mummyDocId;
    }

    public void setMummyDocId(String mummyDocId) {
        this.mummyDocId = mummyDocId;
    }
}
