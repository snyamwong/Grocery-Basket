package edu.wit.mobileapp.mealprepplanner;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
    Define model class by extending RealmObject

    NOTE: Meal has a one to many relationship to Ingredients
 */
public class Ingredient extends RealmObject
{
    @PrimaryKey
    private int ingredientID;

    private String name;

    private String category;

    // Public no-args constructor
    public Ingredient ()
    {

    }

    // Public custom constructor
    public Ingredient (int ingredientID, String name, String category)
    {
        this.setIngredientID(ingredientID);
        this.setName(name);
        this.setCategory(category);
    }

    public int getIngredientID ()
    {
        return ingredientID;
    }

    public void setIngredientID (int ingredientID)
    {
        this.ingredientID = ingredientID;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }
}
