package com.neu.zincIon.pojo;

public class User {
    private String userId; //主键
    private String userPwd;
    private String userMail;
    private String userJob;

    public User(String userId, String userPwd, String userMail, String userJob) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userMail = userMail;
        this.userJob = userJob;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserId() {
        return userId;
    }



    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Override
    public String toString() {
       return userId + " "  + " " + userPwd + " " + userMail + " " + userJob;
    }
}
