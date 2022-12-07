package com.example.bikehire.Model;

public class FeedbackModel {
    private int feedbackId,feedbackCustomerId,feedbackCompanyId,getFeedbackBookingId;
    private float feedbackCompanyRating;
    private String feedbackSatisfaction, feedbackSuggestion,feedbackDate;

    public FeedbackModel(int feedbackId, int feedbackCustomerId, int feedbackCompanyId, int getFeedbackBookingId, float feedbackCompanyRating, String feedbackSatisfaction, String feedbackSuggestion, String feedbackDate) {
        this.feedbackId = feedbackId;
        this.feedbackCustomerId = feedbackCustomerId;
        this.feedbackCompanyId = feedbackCompanyId;
        this.getFeedbackBookingId = getFeedbackBookingId;
        this.feedbackCompanyRating = feedbackCompanyRating;
        this.feedbackSatisfaction = feedbackSatisfaction;
        this.feedbackSuggestion = feedbackSuggestion;
        this.feedbackDate = feedbackDate;
    }

    @Override
    public String toString() {
        return "FeedbackModel{" +
                "feedbackId=" + feedbackId +
                ", feedbackCustomerId=" + feedbackCustomerId +
                ", feedbackCompanyId=" + feedbackCompanyId +
                ", getFeedbackBikeId=" + getFeedbackBookingId +
                ", feedbackCompanyRating=" + feedbackCompanyRating +
                ", feedbackSatisfaction='" + feedbackSatisfaction + '\'' +
                ", feedbackSuggestion='" + feedbackSuggestion + '\'' +
                ", feedbackDate='" + feedbackDate + '\'' +
                '}';
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getFeedbackCustomerId() {
        return feedbackCustomerId;
    }

    public void setFeedbackCustomerId(int feedbackCustomerId) {
        this.feedbackCustomerId = feedbackCustomerId;
    }

    public int getFeedbackCompanyId() {
        return feedbackCompanyId;
    }

    public void setFeedbackCompanyId(int feedbackCompanyId) {
        this.feedbackCompanyId = feedbackCompanyId;
    }

    public int getGetFeedbackBookingId() {
        return getFeedbackBookingId;
    }

    public void setGetFeedbackBookingId(int getFeedbackBookingId) {
        this.getFeedbackBookingId = getFeedbackBookingId;
    }

    public float getFeedbackCompanyRating() {
        return feedbackCompanyRating;
    }

    public void setFeedbackCompanyRating(float feedbackCompanyRating) {
        this.feedbackCompanyRating = feedbackCompanyRating;
    }

    public String getFeedbackSatisfaction() {
        return feedbackSatisfaction;
    }

    public void setFeedbackSatisfaction(String feedbackSatisfaction) {
        this.feedbackSatisfaction = feedbackSatisfaction;
    }

    public String getFeedbackSuggestion() {
        return feedbackSuggestion;
    }

    public void setFeedbackSuggestion(String feedbackSuggestion) {
        this.feedbackSuggestion = feedbackSuggestion;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
}
