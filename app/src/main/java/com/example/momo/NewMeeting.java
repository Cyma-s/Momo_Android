package com.example.momo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NewMeeting implements Parcelable {

    private String meetingName, type;
    private int meetingNum;
    private int wantTimeStart, wantTimeEnd;
    private int requiredTime;
    private ArrayList<Date> term = new ArrayList<>();

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
