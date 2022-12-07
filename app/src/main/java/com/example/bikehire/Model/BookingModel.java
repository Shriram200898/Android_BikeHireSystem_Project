package com.example.bikehire.Model;

import android.graphics.Bitmap;

public class BookingModel {
    private int bookingId,bookingCustomerId,bookingCompanyId,bookingBikeId,bookingStatus,bookingNoOfHoursDaysWeek;
    private String bookingDate,bookingJourneyDate,bookingReturnDate,bookingDeliveryAddress,bookingtype,deliverytime,returntime;
    private Bitmap bookingDrivingLicense,bokingDeliveryPhoto;
    private Double bookingAmount;
    private float bookingRating;

    public BookingModel(int bookingId, int bookingCustomerId, int bookingCompanyId, int bookingBikeId, int bookingStatus, int bookingNoOfHoursDaysWeek, String bookingDate, String bookingJourneyDate, String bookingReturnDate, String bookingDeliveryAddress, String bookingtype, String deliverytime, String returntime, Bitmap bookingDrivingLicense, Bitmap bokingDeliveryPhoto, Double bookingAmount, float bookingRating) {
        this.bookingId = bookingId;
        this.bookingCustomerId = bookingCustomerId;
        this.bookingCompanyId = bookingCompanyId;
        this.bookingBikeId = bookingBikeId;
        this.bookingStatus = bookingStatus;
        this.bookingNoOfHoursDaysWeek = bookingNoOfHoursDaysWeek;
        this.bookingDate = bookingDate;
        this.bookingJourneyDate = bookingJourneyDate;
        this.bookingReturnDate = bookingReturnDate;
        this.bookingDeliveryAddress = bookingDeliveryAddress;
        this.bookingtype = bookingtype;
        this.deliverytime = deliverytime;
        this.returntime = returntime;
        this.bookingDrivingLicense = bookingDrivingLicense;
        this.bokingDeliveryPhoto = bokingDeliveryPhoto;
        this.bookingAmount = bookingAmount;
        this.bookingRating = bookingRating;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "bookingId=" + bookingId +
                ", bookingCustomerId=" + bookingCustomerId +
                ", bookingCompanyId=" + bookingCompanyId +
                ", bookingBikeId=" + bookingBikeId +
                ", bookingStatus=" + bookingStatus +
                ", bookingNoOfHoursDaysWeek=" + bookingNoOfHoursDaysWeek +
                ", bookingDate='" + bookingDate + '\'' +
                ", bookingJourneyDate='" + bookingJourneyDate + '\'' +
                ", bookingReturnDate='" + bookingReturnDate + '\'' +
                ", bookingDeliveryAddress='" + bookingDeliveryAddress + '\'' +
                ", bookingtype='" + bookingtype + '\'' +
                ", deliverytime='" + deliverytime + '\'' +
                ", returntime='" + returntime + '\'' +
                ", bookingDrivingLicense=" + bookingDrivingLicense +
                ", bokingDeliveryPhoto=" + bokingDeliveryPhoto +
                ", bookingAmount=" + bookingAmount +
                ", bookingRating=" + bookingRating +
                '}';
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getBookingCustomerId() {
        return bookingCustomerId;
    }

    public void setBookingCustomerId(int bookingCustomerId) {
        this.bookingCustomerId = bookingCustomerId;
    }

    public int getBookingCompanyId() {
        return bookingCompanyId;
    }

    public void setBookingCompanyId(int bookingCompanyId) {
        this.bookingCompanyId = bookingCompanyId;
    }

    public int getBookingBikeId() {
        return bookingBikeId;
    }

    public void setBookingBikeId(int bookingBikeId) {
        this.bookingBikeId = bookingBikeId;
    }

    public int getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getBookingNoOfHoursDaysWeek() {
        return bookingNoOfHoursDaysWeek;
    }

    public void setBookingNoOfHoursDaysWeek(int bookingNoOfHoursDaysWeek) {
        this.bookingNoOfHoursDaysWeek = bookingNoOfHoursDaysWeek;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingJourneyDate() {
        return bookingJourneyDate;
    }

    public void setBookingJourneyDate(String bookingJourneyDate) {
        this.bookingJourneyDate = bookingJourneyDate;
    }

    public String getBookingReturnDate() {
        return bookingReturnDate;
    }

    public void setBookingReturnDate(String bookingReturnDate) {
        this.bookingReturnDate = bookingReturnDate;
    }

    public String getBookingDeliveryAddress() {
        return bookingDeliveryAddress;
    }

    public void setBookingDeliveryAddress(String bookingDeliveryAddress) {
        this.bookingDeliveryAddress = bookingDeliveryAddress;
    }

    public String getBookingtype() {
        return bookingtype;
    }

    public void setBookingtype(String bookingtype) {
        this.bookingtype = bookingtype;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getReturntime() {
        return returntime;
    }

    public void setReturntime(String returntime) {
        this.returntime = returntime;
    }

    public Bitmap getBookingDrivingLicense() {
        return bookingDrivingLicense;
    }

    public void setBookingDrivingLicense(Bitmap bookingDrivingLicense) {
        this.bookingDrivingLicense = bookingDrivingLicense;
    }

    public Bitmap getBokingDeliveryPhoto() {
        return bokingDeliveryPhoto;
    }

    public void setBokingDeliveryPhoto(Bitmap bokingDeliveryPhoto) {
        this.bokingDeliveryPhoto = bokingDeliveryPhoto;
    }

    public Double getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(Double bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public float getBookingRating() {
        return bookingRating;
    }

    public void setBookingRating(float bookingRating) {
        this.bookingRating = bookingRating;
    }
}
