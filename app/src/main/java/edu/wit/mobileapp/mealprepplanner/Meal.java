package edu.wit.mobileapp.mealprepplanner;

import android.widget.ImageView;

public class Meal {
    private int id;
    private ImageView image;
    private String name;
    private int amount;

    public Meal(int id, ImageView image, String name, int amount) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId(){
        return id;
    }

    public ImageView getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}
