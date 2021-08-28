package com.example.momo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NewMeeting implements Parcelable {

    private String meetingName, type;
    private int meetingNum;
    private int wantTimeStart, wantTimeEnd;
    private int requiredTime;



    public NewMeeting(String meetingName, String type, int meetingNum, int wantTimeStart, int wantTimeEnd, int requiredTime, ArrayList<Date> term) {
        this.meetingName = meetingName;
        this.type = type;
        this.meetingNum = meetingNum;
        this.wantTimeStart = wantTimeStart;
        this.wantTimeEnd = wantTimeEnd;
        this.requiredTime = requiredTime;
        this.term = term;
    }

    private ArrayList<Date> term = new ArrayList<>();

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMeetingNum() {
        return meetingNum;
    }

    public void setMeetingNum(int meetingNum) {
        this.meetingNum = meetingNum;
    }

    public int getWantTimeStart() {
        return wantTimeStart;
    }

    public void setWantTimeStart(int wantTimeStart) {
        this.wantTimeStart = wantTimeStart;
    }

    public int getWantTimeEnd() {
        return wantTimeEnd;
    }

    public void setWantTimeEnd(int wantTimeEnd) {
        this.wantTimeEnd = wantTimeEnd;
    }

    public int getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(int requiredTime) {
        this.requiredTime = requiredTime;
    }

    public ArrayList<Date> getTerm() {
        return term;
    }

    public void setTerm(ArrayList<Date> term) {
        this.term = term;
    }

    protected NewMeeting(Parcel in) {
        meetingName = in.readString();
        type = in.readString();
        meetingNum = in.readInt();
        wantTimeStart = in.readInt();
        wantTimeEnd = in.readInt();
        requiredTime = in.readInt();
    }

    public static final Creator<NewMeeting> CREATOR = new Creator<NewMeeting>() {
        @Override
        public NewMeeting createFromParcel(Parcel in) {
            return new NewMeeting(in);
        }

        @Override
        public NewMeeting[] newArray(int size) {
            return new NewMeeting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(meetingName);
        parcel.writeString(type);
        parcel.writeInt(meetingNum);
        parcel.writeInt(wantTimeStart);
        parcel.writeInt(wantTimeEnd);
        parcel.writeInt(requiredTime);
    }
}
