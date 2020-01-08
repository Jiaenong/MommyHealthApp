package com.example.mommyhealthapp.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class MommyHealthInfo implements Parcelable {
    private String mommyId;
    private String year;
    private String month;
    private String status;

    public MommyHealthInfo(){}

    public MommyHealthInfo(String mommyId, String year, String month, String status)
    {
        this.mommyId = mommyId;
        this.year = year;
        this.month = month;
        this.status = status;
    }

    protected MommyHealthInfo(Parcel in) {
        mommyId = in.readString();
        year = in.readString();
        month = in.readString();
        status = in.readString();
    }

    public static final Creator<MommyHealthInfo> CREATOR = new Creator<MommyHealthInfo>() {
        @Override
        public MommyHealthInfo createFromParcel(Parcel in) {
            return new MommyHealthInfo(in);
        }

        @Override
        public MommyHealthInfo[] newArray(int size) {
            return new MommyHealthInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mommyId);
        dest.writeString(year);
        dest.writeString(month);
        dest.writeString(status);
    }
}
