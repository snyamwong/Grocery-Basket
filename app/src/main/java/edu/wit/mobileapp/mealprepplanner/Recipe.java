package edu.wit.mobileapp.mealprepplanner;

public class Recipe
{
    private int recipeID;

    private String name;

    private String description;

    private String instructions;


    public Recipe() {}

    public Recipe(int recipeID, String name, String description, String instructions)
    {
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
    }

    public String getInstructions()
    {
        return instructions;
    }

    public void setInstructions(String instructions)
    {
        this.instructions = instructions;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRecipeID()
    {
        return recipeID;
    }

    public void setRecipeID(int recipeID)
    {
        this.recipeID = recipeID;
    }
}
