package com.example.mommyhealthapp.Class;

public class MedicalPersonnel {

    private String name;
    private String password;
    private String IC;
    private String address;
    private String email;
    private String medicalPersonnelId;
    private String ImageUrl;
    private String type;

    public MedicalPersonnel(){}

    public MedicalPersonnel(String name, String password, String IC, String address, String email, String medicalPersonnelId, String ImageUrl, String type)
    {
        this.name = name;
        this.password = password;
        this.IC = IC;
        this.address = address;
        this.email = email;
        this.medicalPersonnelId = medicalPersonnelId;
        this.ImageUrl = ImageUrl;
        this.type = type;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedicalPersonnelId() {
        return medicalPersonnelId;
    }

    public void setMedicalPersonnelId(String medicalPersonnelId) {
        this.medicalPersonnelId = medicalPersonnelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
