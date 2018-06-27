package edu.wit.mobileapp.mealprepplanner;

/**
 * Define model class by extending RealmObject
 * <p>
 * NOTE: Meal has a one to many relationship to Ingredient
 *
 * @author Jason Fagerberg
 */


public class Meal
{
    private int id;

    // R.id for this meals picture
    private int image;

    // name of meal
    private String name;

    // # of servings
    private int amount;

    // Public no args constructor
    public Meal() {}

    public Meal(int id, int image, String name, int amount)
    {
        //TODO: Add Ingredients Array List to hold ingredients in meal
        this.id = id;
        this.image = image;
        this.name = name;
        this.amount = amount;
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
}
