package com.example.dataapp;
// Create user data schema
public class Userdata {
    // Init variables
    String name;
    double value;

    // Assign user data
    public Userdata(String name, double value) {
        this.name = name;
        this.value = value;
    }
    // Getter functions
    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
