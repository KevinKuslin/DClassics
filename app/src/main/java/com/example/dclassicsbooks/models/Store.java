package com.example.dclassicsbooks.models;

public class Store {

    private String name;
    private String location;
    private String description;
    private float rating;
    private int image;

    public Store(String name,
                 String location,
                 String description,
                 float rating,
                 int image) {

        this.name = name;
        this.location = location;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public int getImage() {
        return image;
    }

}