package com.neu.zincIon.pojo;

public class UserTest {
    private String id;
    private String pwd;
    private String mail;

    private String job;

    public UserTest(String id,  String pwd, String mail, String job) {
        this.id = id;
        this.pwd = pwd;
        this.mail = mail;
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public UserTest() {
    }

    public String getid() {
        return id;
    }



    public String getpwd() {
        return pwd;
    }

    public void setpwd(String pwd) {
        this.pwd = pwd;
    }

    public String getmail() {
        return mail;
    }

    public void setmail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return id + " "  + " " + pwd + " " + mail + " " + job;
    }
}
