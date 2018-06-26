package edu.wit.mobileapp.mealprepplanner;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Recipe extends RealmObject
{
    @PrimaryKey
    private int recipeID;

    private String name;

    private String description;

    private String instructions;

    private RealmList<RecipeIngredients> ingredients;

    public Recipe () {}

    public Recipe (int recipeID, String name, String description, String instructions, RealmList<RecipeIngredients> ingredients)
    {
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public String getInstructions ()
    {
        return instructions;
    }

    public void setInstructions (String instructions)
    {
        this.instructions = instructions;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public int getRecipeID ()
    {
        return recipeID;
    }

    public void setRecipeID (int recipeID)
    {
        this.recipeID = recipeID;
    }

    public RealmList<RecipeIngredients> getIngredients ()
    {
        return ingredients;
    }

    public void setIngredients (RealmList<RecipeIngredients> ingredients)
    {
        this.ingredients = ingredients;
    }
}
