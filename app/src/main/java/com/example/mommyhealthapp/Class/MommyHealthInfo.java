package com.example.mommyhealthapp.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class MommyHealthInfo {
    private String mommyId;
    private String year;
    private String month;
    private String status;
    private String healthInfoId;

    public MommyHealthInfo(){}

    public MommyHealthInfo(String mommyId, String year, String month, String status, String healthInfoId)
    {
        this.mommyId = mommyId;
        this.year = year;
        this.month = month;
        this.status = status;
        this.healthInfoId = healthInfoId;
    }

    public String getHealthInfoId() {
        return healthInfoId;
    }

    public void setHealthInfoId(String healthInfoId) {
        this.healthInfoId = healthInfoId;
    }

    public String getMommyId() {
        return mommyId;
    }

    public void setMommyId(String mommyId) {
        this.mommyId = mommyId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
