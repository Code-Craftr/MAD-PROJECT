package com.example.qmanageapplication.models;

import com.google.gson.annotations.SerializedName;

public class FoodItem {
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;

    @SerializedName("is_veg")
    private boolean isVeg;

    @SerializedName("image_res_name")
    private String imageResName;

    @SerializedName("is_available")
    private boolean isAvailable;

    public FoodItem(int id, String name, String description, double price, String category,
                    boolean isVeg, String imageResName, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isVeg = isVeg;
        this.imageResName = imageResName;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isVeg() { return isVeg; }
    public String getImageResName() { return imageResName; }
    public boolean isAvailable() { return isAvailable; }
}
