package edu.wit.mobileapp.mealprepplanner;

public class RecipeIngredients
{
    private int recipeIngredientsID;

    private int recipeID;

    private int ingredientID;

    private double quantity;

    private String unit;

    public RecipeIngredients() {}

    public RecipeIngredients(int recipeIngredientsID, int recipeID, int ingredientID, double quantity, String unit)
    {
        this.recipeIngredientsID = recipeIngredientsID;
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getRecipeIngredientsID()
    {
        return recipeIngredientsID;
    }

    public void setRecipeIngredientsID(int recipeIngredientsID)
    {
        this.recipeIngredientsID = recipeIngredientsID;
    }

    public int getRecipeID()
    {
        return recipeID;
    }

    public void setRecipeID(int recipeID)
    {
        this.recipeID = recipeID;
    }

    public int getIngredientID()
    {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID)
    {
        this.ingredientID = ingredientID;
    }

    public double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }
}
