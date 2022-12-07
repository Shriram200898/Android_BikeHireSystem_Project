package com.example.bikehire.Model;

import android.graphics.Bitmap;

public class BikeModel {
    private int bikeNo, bikestatus,mileage,companyid,model;
    private String bikename, bikecategory, description,starttype,engine,bikeregno,fueltype;
    private Double bikehourrent,bikedayrent,bikeweekrent, deposit;
    private Bitmap bikeimage,bikerc;



    public BikeModel(int bikeNo, String bikeregno, int companyid, String bikename, String bikecategory, String description, String starttype, String engine,String fueltype, Double bikehourrent, Double bikedayrent, Double bikeweekrent, Double deposit, int bikestatus, int mileage, int model,Bitmap bikeimage, Bitmap bikerc) {
        this.bikeNo = bikeNo;
        this.bikestatus = bikestatus;
        this.mileage = mileage;
        this.companyid = companyid;
        this.model = model;
        this.bikename = bikename;
        this.bikecategory = bikecategory;
        this.description = description;
        this.starttype = starttype;
        this.engine = engine;
        this.fueltype=fueltype;
        this.bikeregno = bikeregno;
        this.bikehourrent = bikehourrent;
        this.bikedayrent = bikedayrent;
        this.bikeweekrent = bikeweekrent;
        this.deposit = deposit;
        this.bikeimage = bikeimage;
        this.bikerc = bikerc;
    }

    @Override
    public String toString() {
        return "BikeModel{" +
                "bikeNo=" + bikeNo +
                ", bikestatus=" + bikestatus +
                ", mileage=" + mileage +
                ", companyid=" + companyid +
                ", model=" + model +
                ", bikename='" + bikename + '\'' +
                ", bikecategory='" + bikecategory + '\'' +
                ", description='" + description + '\'' +
                ", starttype='" + starttype + '\'' +
                ", engine='" + engine + '\'' +
                ", bikeregno='" + bikeregno + '\'' +
                ", fueltype='" + fueltype + '\'' +
                ", bikehourrent=" + bikehourrent +
                ", bikedayrent=" + bikedayrent +
                ", bikeweekrent=" + bikeweekrent +
                ", deposit=" + deposit +
                ", bikeimage=" + bikeimage +
                ", bikerc=" + bikerc +
                '}';
    }

    public int getBikeNo() {
        return bikeNo;
    }

    public void setBikeNo(int bikeNo) {
        this.bikeNo = bikeNo;
    }

    public int getBikestatus() {
        return bikestatus;
    }

    public void setBikestatus(int bikestatus) {
        this.bikestatus = bikestatus;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getBikename() {
        return bikename;
    }

    public void setBikename(String bikename) {
        this.bikename = bikename;
    }

    public String getBikecategory() {
        return bikecategory;
    }

    public void setBikecategory(String bikecategory) {
        this.bikecategory = bikecategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStarttype() {
        return starttype;
    }

    public void setStarttype(String starttype) {
        this.starttype = starttype;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getBikeregno() {
        return bikeregno;
    }

    public void setBikeregno(String bikeregno) {
        this.bikeregno = bikeregno;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }

    public Double getBikehourrent() {
        return bikehourrent;
    }

    public void setBikehourrent(Double bikehourrent) {
        this.bikehourrent = bikehourrent;
    }

    public Double getBikedayrent() {
        return bikedayrent;
    }

    public void setBikedayrent(Double bikedayrent) {
        this.bikedayrent = bikedayrent;
    }

    public Double getBikeweekrent() {
        return bikeweekrent;
    }

    public void setBikeweekrent(Double bikeweekrent) {
        this.bikeweekrent = bikeweekrent;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Bitmap getBikeimage() {
        return bikeimage;
    }

    public void setBikeimage(Bitmap bikeimage) {
        this.bikeimage = bikeimage;
    }

    public Bitmap getBikerc() {
        return bikerc;
    }

    public void setBikerc(Bitmap bikerc) {
        this.bikerc = bikerc;
    }
}
