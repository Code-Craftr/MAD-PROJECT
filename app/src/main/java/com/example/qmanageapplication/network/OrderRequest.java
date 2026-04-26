package com.example.qmanageapplication.network;

import java.util.List;

public class OrderRequest {
    private int userId;
    private int outletId;
    private double totalAmount;
    private List<OrderItemRequest> items;

    public OrderRequest(int userId, int outletId, double totalAmount, List<OrderItemRequest> items) {
        this.userId = userId;
        this.outletId = outletId;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public static class OrderItemRequest {
        private int menuItemId;
        private int quantity;
        private double price;

        public OrderItemRequest(int menuItemId, int quantity, double price) {
            this.menuItemId = menuItemId;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
