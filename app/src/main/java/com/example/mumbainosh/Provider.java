package com.example.mumbainosh;

public class Provider {
    public String name;
    public String address;
    public String pinCode;
    public String timing;
    public long timestamp;
    public double price;

    public Provider() {
     }

    public Provider(String name, String address, String pinCode, String timing, long timestamp,double price) {
        this.name = name;
        this.address = address;
        this.pinCode = pinCode;
        this.timing = timing;
        this.timestamp = timestamp;
        this.price = price;
    }
    public String getPostalCode() {
        return pinCode;
    }


}
