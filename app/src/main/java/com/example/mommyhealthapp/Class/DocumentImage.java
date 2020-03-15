package com.example.mommyhealthapp.Class;

import java.util.Date;

public class DocumentImage {
    private String imageId;
    private String title;
    private String description;
    private String imageURL;
    private String medicalPersonnelId;
    private Date createdDate;

    public DocumentImage(){

    }

    public DocumentImage(String imageId, String title, String description, String imageURL, String medicalPersonnelId, Date createdDate) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.medicalPersonnelId = medicalPersonnelId;
        this.createdDate = createdDate;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
