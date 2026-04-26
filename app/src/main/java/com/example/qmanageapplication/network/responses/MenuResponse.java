package com.example.qmanageapplication.network.responses;

import com.example.qmanageapplication.models.FoodItem;
import java.util.List;

public class MenuResponse {
    private boolean success;
    private List<FoodItem> menuItems;

    public boolean isSuccess() { return success; }
    public List<FoodItem> getMenuItems() { return menuItems; }
}
