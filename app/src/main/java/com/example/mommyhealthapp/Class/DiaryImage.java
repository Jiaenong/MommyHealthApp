package com.example.mommyhealthapp.Class;

import java.util.Date;

public class DiaryImage {
    private String imageId;
    private String title;
    private String description;
    private String imageURL;
    private String motherId;
    private Date createdDate;

    public DiaryImage() {

    }

    public DiaryImage(String imageId, String title, String description, String imageURL, String motherId, Date createdDate) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.motherId = motherId;
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

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
