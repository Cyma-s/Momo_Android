package com.example.momo;

public class Meeting {
    // 모임 리사이클러뷰에 들어갈 아이템 클래스 구성
    String meetingDay, meetingName;
    int meetingType;
    boolean alert;
    int dDay;

    public Meeting(String meetingDay, String meetingName, int meetingType, boolean alert, int dDay) {
        this.meetingDay = meetingDay;
        this.meetingName = meetingName;
        this.meetingType = meetingType;
        this.alert = alert;
        this.dDay = dDay;
    }

    public String getMeetingDay() {
        return meetingDay;
    }

    public void setMeetingDay(String meetingDay) {
        this.meetingDay = meetingDay;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public int getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(int meetingType) {
        this.meetingType = meetingType;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public int getdDay() {
        return dDay;
    }

    public void setdDay(int dDay) {
        this.dDay = dDay;
    }
}
