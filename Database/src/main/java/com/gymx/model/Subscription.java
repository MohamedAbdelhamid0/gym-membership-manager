package com.gymx.model;

public class Subscription {
    private Integer subscriptionId;
    private Integer memberId;
    private String startDate;
    private String endDate;
    private double amountPaid;
    private String subscriptionType;

    public Integer getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(Integer subscriptionId) { this.subscriptionId = subscriptionId; }
    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    public String getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(String subscriptionType) { this.subscriptionType = subscriptionType; }
}