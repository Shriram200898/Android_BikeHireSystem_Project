package com.example.bikehire.Model;

import android.graphics.Bitmap;

public class CompanyModel {
    private int compid, isactive;
    private String compname, compemail, comppwd, compphoneno, compaddress,regDate;
    private Bitmap compprofileimage;
    private float comprating;

    public CompanyModel(int compid, String compname, String compemail, String comppwd, String compphoneno, String compaddress, String regDate, Bitmap compprofileimage, int isactive, float comprating) {
        this.compid = compid;
        this.compname = compname;
        this.compemail = compemail;
        this.comppwd = comppwd;
        this.compphoneno = compphoneno;
        this.compaddress = compaddress;
        this.regDate = regDate;
        this.compprofileimage = compprofileimage;
        this.isactive = isactive;
        this.comprating = comprating;
    }

    @Override
    public String toString() {
        return "CompanyModel{" +
                "compid=" + compid +
                ", isactive=" + isactive +
                ", compname='" + compname + '\'' +
                ", compemail='" + compemail + '\'' +
                ", comppwd='" + comppwd + '\'' +
                ", compphoneno='" + compphoneno + '\'' +
                ", compaddress='" + compaddress + '\'' +
                ", regDate='" + regDate + '\'' +
                ", compprofileimage=" + compprofileimage +
                ", comprating=" + comprating +
                '}';
    }

    public int getCompid() {
        return compid;
    }

    public void setCompid(int compid) {
        this.compid = compid;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public String getCompemail() {
        return compemail;
    }

    public void setCompemail(String compemail) {
        this.compemail = compemail;
    }

    public String getComppwd() {
        return comppwd;
    }

    public void setComppwd(String comppwd) {
        this.comppwd = comppwd;
    }

    public String getCompphoneno() {
        return compphoneno;
    }

    public void setCompphoneno(String compphoneno) {
        this.compphoneno = compphoneno;
    }

    public String getCompaddress() {
        return compaddress;
    }

    public void setCompaddress(String compaddress) {
        this.compaddress = compaddress;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Bitmap getCompprofileimage() {
        return compprofileimage;
    }

    public void setCompprofileimage(Bitmap compprofileimage) {
        this.compprofileimage = compprofileimage;
    }

    public float getComprating() {
        return comprating;
    }

    public void setComprating(float comprating) {
        this.comprating = comprating;
    }
}
