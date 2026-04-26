package com.example.qmanageapplication.network.responses;

public class OrderResponse {
    private boolean success;
    private String message;
    private int orderId;
    private String tokenNumber;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public int getOrderId() { return orderId; }
    public String getTokenNumber() { return tokenNumber; }
}
