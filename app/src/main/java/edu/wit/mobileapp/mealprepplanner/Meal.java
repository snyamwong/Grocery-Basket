package edu.wit.mobileapp.mealprepplanner;

import java.util.ArrayList;

/**
 * Class to represent a Meal in MealListFragment
 */
public class Meal
{
    private int id;

    // TODO switch image to BitMap or byte[] to store image in SQL
    // R.id for this meals picture
    private int image;

    // name of meal
    private String name;

    // # of servings
    private int amount;
    private ArrayList<Ingredient> ingredients;

    // Public no args constructor
    public Meal() {}
  
    public Meal(int id, int image, String name, int amount, ArrayList<Ingredient> ingredients) {
    // TODO switch image to BitMap or byte[] to store image in SQL
        this.id = id;
        this.image = image;
        this.name = name;
        this.amount = amount;
        this.ingredients = ingredients;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getId() { return id; }

    public int getImage()
    {
        return image;
    }

    public String getName()
    {
        return name;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients){this.ingredients = ingredients;}

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
