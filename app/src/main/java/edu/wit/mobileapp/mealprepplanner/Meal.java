package edu.wit.mobileapp.mealprepplanner;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Define model class by extending RealmObject
 *
 * NOTE: Meal has a one to many relationship to Ingredient
 *
 * @author Jason Fagerberg
 */


public class Meal extends RealmObject
{
    @PrimaryKey
    private long id;

    //R.id for this meals picture
    private int imageID;

    //name of meal
    private String name;

    //# of servings
    private int amount;

    // Declaring a one to many relationship with Ingredient
    private RealmList<Ingredient> ingredients;

    Meal (int id, int imageID, String name, int amount, RealmList<Ingredient> ingredients)
    {
        setId(id);
        setImageID(imageID);
        setName(name);
        setAmount(amount);
        setIngredients(ingredients);
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public void setImageID (int image)
    {
        this.imageID = image;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public void setAmount (int amount)
    {
        this.amount = amount;
    }

    public void setIngredients (RealmList<Ingredient> ingredients) { this.ingredients = ingredients; }

    public long getId () { return id; }

    public int getImageID ()
    {
        return imageID;
    }

    public String getName ()
    {
        return name;
    }

    public int getAmount ()
    {
        return amount;
    }

    public RealmList<Ingredient> getIngredients () { return ingredients; }
}
