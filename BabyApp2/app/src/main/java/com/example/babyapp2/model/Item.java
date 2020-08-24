package com.example.babyapp2.model;

public class Item {
    private int id;
    private String itemName;
    private String itemColor;
    private int itemQuentity;
    private int itemSize;
    private String dateItemAdded;

    public Item() {
    }

    public Item(int id, String itemName, String itemColor, int itemQuentity, int itemSize, String dateItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemQuentity = itemQuentity;
        this.itemSize = itemSize;
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getItemQuentity() {
        return itemQuentity;
    }

    public void setItemQuentity(int itemQuentity) {
        this.itemQuentity = itemQuentity;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
