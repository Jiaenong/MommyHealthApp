package com.example.mommyhealthapp.Class;

import java.util.Date;

public class BabyKickCount {
    private String BabyKickInfoId;
    private String mommyId;
    private Date createdDate;

    public BabyKickCount(){}

    public BabyKickCount(String babyKickInfoId, String mommyId, Date createdDate) {
        BabyKickInfoId = babyKickInfoId;
        this.mommyId = mommyId;
        this.createdDate = createdDate;
    }

    public String getBabyKickInfoId() {
        return BabyKickInfoId;
    }

    public void setBabyKickInfoId(String babyKickInfoId) {
        BabyKickInfoId = babyKickInfoId;
    }

    public String getMommyId() {
        return mommyId;
    }

    public void setMommyId(String mommyId) {
        this.mommyId = mommyId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
