package com.example.qmanageapplication.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to manage cart items across activities.
 */
public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems;
    private int currentOutletId = -1;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(FoodItem foodItem) {
        // If cart is from a different outlet, clear it
        if (currentOutletId != -1 && currentOutletId != foodItem.getOutletId()) {
            cartItems.clear();
        }
        currentOutletId = foodItem.getOutletId();

        // Check if item already exists in cart
        for (CartItem cartItem : cartItems) {
            if (cartItem.getFoodItem().getId() == foodItem.getId()) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return;
            }
        }
        // New item
        cartItems.add(new CartItem(foodItem, 1));
    }

    public int getCurrentOutletId() {
        return currentOutletId;
    }

    public void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
            if (cartItems.isEmpty()) currentOutletId = -1;
        }
    }

    public void increaseQuantity(int position) {
        if (position >= 0 && position < cartItems.size()) {
            CartItem item = cartItems.get(position);
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public void decreaseQuantity(int position) {
        if (position >= 0 && position < cartItems.size()) {
            CartItem item = cartItems.get(position);
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                cartItems.remove(position);
            }
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getItemCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public double getTax() {
        return getSubtotal() * 0.05; // 5% tax
    }

    public double getTotal() {
        return getSubtotal() + getTax();
    }

    public void clearCart() {
        cartItems.clear();
        currentOutletId = -1;
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
