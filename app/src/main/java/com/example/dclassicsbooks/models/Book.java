package com.example.dclassicsbooks.models;

public class Book {

    private String title;
    private String author;
    private float rating;
    private int image;

    public Book(String title, String author, float rating, int image) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public float getRating() {
        return rating;
    }

    public int getImage() {
        return image;
    }
}