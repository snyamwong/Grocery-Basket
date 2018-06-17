package edu.wit.mobileapp.mealprepplanner;

import android.widget.ImageView;

public class Meal {
    private int id;
    private int imageID;
    private String name;
    private int amount;

    public Meal(int id, int image, String name, int amount) {
        this.id = id;
        this.imageID = image;
        this.name = name;
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageID(int image) {
        this.imageID = image;
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

    public int getImageID() {
        return imageID;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}
