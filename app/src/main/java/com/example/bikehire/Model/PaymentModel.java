package com.example.bikehire.Model;

public class PaymentModel {
    private int paymentCustomerId,paymentBookingId,paymentStatus;
    private String paymentId,paymentDate;
    private  Double paymentAmount;

    public PaymentModel(String paymentId, int paymentCustomerId, int paymentBookingId, int paymentStatus, String paymentDate, Double paymentAmount) {
        this.paymentId = paymentId;
        this.paymentCustomerId = paymentCustomerId;
        this.paymentBookingId = paymentBookingId;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "PaymentModel{" +
                "paymentId=" + paymentId +
                ", paymentCustomerId=" + paymentCustomerId +
                ", paymentBookingId=" + paymentBookingId +
                ", paymentStatus=" + paymentStatus +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentAmount=" + paymentAmount +
                '}';
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getPaymentCustomerId() {
        return paymentCustomerId;
    }

    public void setPaymentCustomerId(int paymentCustomerId) {
        this.paymentCustomerId = paymentCustomerId;
    }

    public int getPaymentBookingId() {
        return paymentBookingId;
    }

    public void setPaymentBookingId(int paymentBookingId) {
        this.paymentBookingId = paymentBookingId;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
