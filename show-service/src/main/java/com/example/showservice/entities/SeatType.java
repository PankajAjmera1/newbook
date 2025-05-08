package com.example.showservice.entities;


public enum SeatType {
    REGULAR("Regular"),
    PREMIUM("Premium"),
    VIP("VIP");

    private final String displayName;

    SeatType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}