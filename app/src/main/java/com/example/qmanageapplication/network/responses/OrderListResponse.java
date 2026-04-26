package com.example.qmanageapplication.network.responses;

import com.example.qmanageapplication.models.Order;
import java.util.List;

public class OrderListResponse {
    private boolean success;
    private List<Order> orders;

    public boolean isSuccess() { return success; }
    public List<Order> getOrders() { return orders; }
}
