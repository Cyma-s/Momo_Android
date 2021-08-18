package com.example.momo;

import java.util.ArrayList;

public class AbleTime {
    // 년도, 월, 일 & 가능 시간이 담겨 있는 ArrayList가 함께 있는 클래스

    int year, month, day;
    ArrayList<StartEndTime> ableTimes = new ArrayList<>();

    public AbleTime(int year, int month, int day, ArrayList<StartEndTime> ableTimes) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.ableTimes = ableTimes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ArrayList<StartEndTime> getAbleTimes() {
        return ableTimes;
    }

    public void setAbleTimes(ArrayList<StartEndTime> ableTimes) {
        this.ableTimes = ableTimes;
    }
}
