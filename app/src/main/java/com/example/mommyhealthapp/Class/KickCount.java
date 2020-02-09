package com.example.mommyhealthapp.Class;

import java.util.Date;

public class KickCount {
    private int firstKick;
    private Date firstKickDate;
    private Date firstKickTime;
    private int lastKick;
    private Date lastKickDate;
    private Date lastKickTime;

    public KickCount(){};

    public KickCount(int firstKick, Date firstKickDate, Date firstKickTime, int lastKick, Date lastKickDate, Date lastKickTime) {
        this.firstKick = firstKick;
        this.firstKickDate = firstKickDate;
        this.firstKickTime = firstKickTime;
        this.lastKick = lastKick;
        this.lastKickTime = lastKickTime;
        this.lastKickDate = lastKickDate;
    }

    public int getFirstKick() {
        return firstKick;
    }

    public void setFirstKick(int firstKick) {
        this.firstKick = firstKick;
    }

    public Date getFirstKickDate() {
        return firstKickDate;
    }

    public void setFirstKickDate(Date firstKickDate) {
        this.firstKickDate = firstKickDate;
    }

    public Date getFirstKickTime() {
        return firstKickTime;
    }

    public void setFirstKickTime(Date firstKickTime) {
        this.firstKickTime = firstKickTime;
    }

    public int getLastKick() {
        return lastKick;
    }

    public void setLastKick(int lastKick) {
        this.lastKick = lastKick;
    }

    public Date getLastKickTime() {
        return lastKickTime;
    }

    public void setLastKickTime(Date lastKickTime) {
        this.lastKickTime = lastKickTime;
    }

    public Date getLastKickDate() {
        return lastKickDate;
    }

    public void setLastKickDate(Date lastKickDate) {
        this.lastKickDate = lastKickDate;
    }
}
