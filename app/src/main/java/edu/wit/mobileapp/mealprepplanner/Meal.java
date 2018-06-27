package edu.wit.mobileapp.mealprepplanner;

/**
 *
 * THIS CLASS JUST HOLDS DATA FOR A MEAL
 *
 * @author: Jason Fagerberg
 */


public class Meal {
    //not really needed
    private int id;
    //R.id for this meals picture
    private int imageID;
    //name of meal
    private String name;
    //# of servings
    private int amount;

    public Meal(int id, int image, String name, int amount) {
        //TODO: Add Ingredients Array List to hold ingredients in meal
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