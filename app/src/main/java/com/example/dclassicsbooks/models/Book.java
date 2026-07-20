package com.example.dclassicsbooks.models;

public class Book {

    private String title;
    private String author;
    private float rating;
    private int image;
    private String category;
    private boolean fiction;

    public Book(String title, String author, float rating, int image) {
        this(title, author, rating, image, "General", false);
    }

    public Book(String title, String author, float rating, int image,
                String category, boolean fiction) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.image = image;
        this.category = category;
        this.fiction = fiction;
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

    public String getCategory() {
        return category;
    }

    public boolean isFiction() {
        return fiction;
    }
}
