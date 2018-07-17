package edu.wit.mobileapp.mealprepplanner;

public class RecipeIngredient
{
    private int recipeID;

    private String recipeName;

    private String ingredientName;

    private String ingredientCategory;

    private double quantity;

    private String unit;

    public RecipeIngredient() {}

    public RecipeIngredient(int recipeID, String recipeName, String ingredientName, String ingredientCategory, double quantity, String unit)
    {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.ingredientName = ingredientName;
        this.ingredientCategory = ingredientCategory;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getRecipeID()
    {
        return recipeID;
    }

    public void setRecipeID(int recipeID)
    {
        this.recipeID = recipeID;
    }

    public String getRecipeName()
    {
        return recipeName;
    }

    public void setRecipeName(String recipeName)
    {
        this.recipeName = recipeName;
    }

    public String getIngredientName()
    {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName)
    {
        this.ingredientName = ingredientName;
    }

    public String getIngredientCategory()
    {
        return ingredientCategory;
    }

    public void setIngredientCategory(String ingredientCategory)
    {
        this.ingredientCategory = ingredientCategory;
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

    @Override
    public String toString()
    {
        return "RecipeIngredient{" +
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientCategory='" + ingredientCategory + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
