package com.neu.zincIon.pojo;

public class Approval {
    private String suid;  //主键
    private String courseName; //主键
    private String cause;
    private String proof;
    private String rejection_reason;
    private String state;

    //动态审批使用
    private int lt;  //动态审批当前主讲教师的order
    private int st;
    private int rjlt; //动态审批驳回的主讲教师的order
    private int rjst;  //动态审批驳回的主管教师的order

    public Approval() {
    }

    public Approval(String suid,String courseName) {
        this.suid = suid;
        this.courseName = courseName;
    }

    public Approval(String suid, String courseName, String cause, String proof, String rejectionReason, String state) {
        this.suid = suid;
        this.courseName = courseName;
        this.cause = cause;
        this.proof = proof;
        this.rejection_reason = rejectionReason;
        this.state = state;   //申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。

        //动态审批使用
        this.lt = -1; //-1代表为空
        this.st = -1;
        this.rjlt = -1;
        this.rjst = -1;

    }

    @Override
    public String toString() {
        return suid+" "+courseName+ " "+cause+" "+proof+" "+rejection_reason+" "+state + " " + lt+ " " + st+ " " + rjlt+ " " + rjst;
    }

    public int getLt() {
        return lt;
    }

    public void setLt(int lt) {
        this.lt = lt;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public int getRjlt() {
        return rjlt;
    }

    public void setRjlt(int rjlt) {
        this.rjlt = rjlt;
    }

    public int getRjst() {
        return rjst;
    }

    public void setRjst(int rjst) {
        this.rjst = rjst;
    }

    public String getRejection_reason() {
        return rejection_reason;
    }

    public void setRejection_reason(String rejection_reason) {
        this.rejection_reason = rejection_reason;
    }

    public String getSuid() {
        return suid;
    }


    public String getCourseName() {
        return courseName;
    }


    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
