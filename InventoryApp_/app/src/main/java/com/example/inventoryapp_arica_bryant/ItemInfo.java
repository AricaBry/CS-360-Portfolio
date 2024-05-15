package com.example.inventoryapp_arica_bryant;

public class ItemInfo {
    // Private variables
    private String itemName;
    private int itemQuantity;

    private int itemId;

    // Constructor
    public ItemInfo(String itemName, int itemQuantity, int itemId) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemId = itemId;
    }

    // Getters and Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
