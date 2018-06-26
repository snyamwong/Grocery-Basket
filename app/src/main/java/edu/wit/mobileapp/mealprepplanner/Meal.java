package edu.wit.mobileapp.mealprepplanner;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Define model class by extending RealmObject
 *
 * NOTE: Meal has a one to many relationship to Ingredient
 *
 * @author Jason Fagerberg
 */


public class Meal extends RealmObject
{
    private int id;

    // R.id for this meals picture
    private int image;

    // name of meal
    private String name;

    // # of servings
    private int amount;

    // Public no args constructor
    public Meal()
    {

    }

    // Public custom constructor
    public Meal (int id, int image, String name, int amount)
    {
        setId(id);
        setImage(image);
        setName(name);
        setAmount(amount);
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public void setImage (int image)
    {
        this.image = image;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public void setAmount (int amount)
    {
        this.amount = amount;
    }

    public int getId () { return id; }

    public int getImage ()
    {
        return image;
    }

    public String getName ()
    {
        return name;
    }

    public int getAmount ()
    {
        return amount;
    }
}
