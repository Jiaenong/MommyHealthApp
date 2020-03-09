package com.example.mommyhealthapp.Class;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Mommy implements Parcelable {
    private String mommyName;
    private String mommyIC;
    private String nationality;
    private String race;
    private String address;
    private String phoneNo;
    private String email;
    private String occupation;
    private int age;
    private String education;
    private String password;
    private String mommyId;
    private int mommyNumber;
    private String status;
    private String mummyImage;
    private String qrcodeImage;
    private String healthInfoId;
    private String deviceToken;

    public Mommy() { }

    public Mommy(String mommyName, String mommyIC, String nationality, String race, String address,
                 String phoneNo, String email, String occupation, int age, String education, String password, String mommyId,
                 int mommyNumber, String status, String mummyImage, String qrcodeImage, String healthInfoId, String deviceToken)
    {
        this.mommyName = mommyName;
        this.mommyIC = mommyIC;
        this.nationality = nationality;
        this.race = race;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.occupation = occupation;
        this.age = age;
        this.education = education;
        this.password = password;
        this.mommyId = mommyId;
        this.mommyNumber = mommyNumber;
        this.status = status;
        this.mummyImage = mummyImage;
        this.qrcodeImage = qrcodeImage;
        this.healthInfoId = healthInfoId;
        this.deviceToken = deviceToken;
    }

    protected Mommy(Parcel in) {
        mommyName = in.readString();
        mommyIC = in.readString();
        nationality = in.readString();
        race = in.readString();
        address = in.readString();
        phoneNo = in.readString();
        email = in.readString();
        occupation = in.readString();
        age = in.readInt();
        education = in.readString();
        password = in.readString();
        mommyId = in.readString();
        mommyNumber = in.readInt();
        status = in.readString();
        mummyImage = in.readString();
        qrcodeImage = in.readString();
        healthInfoId = in.readString();
        deviceToken = in.readString();
    }

    public static final Creator<Mommy> CREATOR = new Creator<Mommy>() {
        @Override
        public Mommy createFromParcel(Parcel in) {
            return new Mommy(in);
        }

        @Override
        public Mommy[] newArray(int size) {
            return new Mommy[size];
        }
    };

    public String getQrcodeImage() {
        return qrcodeImage;
    }

    public void setQrcodeImage(String qrcodeImage) {
        this.qrcodeImage = qrcodeImage;
    }

    public String getMummyImage() {
        return mummyImage;
    }

    public void setMummyImage(String mummyImage) {
        this.mummyImage = mummyImage;
    }

    public String getMommyId() {
        return mommyId;
    }

    public void setMommyId(String mommyId) {
        this.mommyId = mommyId;
    }

    public int getMommyNumber() {
        return mommyNumber;
    }

    public void setMommyNumber(int mommyNumber) {
        this.mommyNumber = mommyNumber;
    }

    public static Creator<Mommy> getCREATOR() {
        return CREATOR;
    }

    public String getMommyName() {
        return mommyName;
    }

    public void setMommyName(String mommyName) {
        this.mommyName = mommyName;
    }

    public String getMommyIC() {
        return mommyIC;
    }

    public void setMommyIC(String mommyIC) {
        this.mommyIC = mommyIC;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHealthInfoId() {
        return healthInfoId;
    }

    public void setHealthInfoId(String healthInfoId) {
        this.healthInfoId = healthInfoId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mommyName);
        dest.writeString(mommyIC);
        dest.writeString(nationality);
        dest.writeString(race);
        dest.writeString(address);
        dest.writeString(phoneNo);
        dest.writeString(email);
        dest.writeString(occupation);
        dest.writeInt(age);
        dest.writeString(education);
        dest.writeString(password);
        dest.writeString(mommyId);
        dest.writeInt(mommyNumber);
        dest.writeString(status);
        dest.writeString(mummyImage);
        dest.writeString(qrcodeImage);
        dest.writeString(healthInfoId);
        dest.writeString(deviceToken);
    }
}
