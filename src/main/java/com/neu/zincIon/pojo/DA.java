package com.neu.zincIon.pojo;

public class DA {
    private String courseName;
    private String tuid;
    private int order;

    public DA() {
    }

    public DA(String courseName, String tuid, int order) {
        this.courseName = courseName;
        this.tuid = tuid;
        this.order = order;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTuid() {
        return tuid;
    }

    public int getOrder() {
        return order;
    }
}
