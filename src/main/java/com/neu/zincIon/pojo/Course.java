package com.neu.zincIon.pojo;

public class Course {
    private String courseName; //主键
    private String begTime;
    private String ltuid;
    private String stuid;

    @Override
    public String toString() {
        return courseName+" "+begTime+ " "+ltuid+" "+stuid;
    }

    public String getNameAndTime() {
        return courseName+" "+begTime;
    }

    public Course() {
    }

    public Course(String courseName, String begTime) {
        this.courseName = courseName;
        this.begTime = begTime;
    }

    public Course(String courseName, String begTime, String ltuid, String stuid) {
        this.courseName = courseName;
        this.begTime = begTime;
        this.ltuid = ltuid;
        this.stuid = stuid;
    }

    public String getCourseName() {
        return courseName;
    }


    public String getbegTime() {
        return begTime;
    }

    public void setbegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getLtuid() {
        return ltuid;
    }

    public void setLtuid(String ltuid) {
        this.ltuid = ltuid;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }
}
