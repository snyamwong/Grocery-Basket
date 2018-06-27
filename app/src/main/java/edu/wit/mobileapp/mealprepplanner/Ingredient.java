package edu.wit.mobileapp.mealprepplanner;

/*
    Class to represent an Ingredient in SQLite (if needed)
 */
//public class Ingredient
//{
//    private int ingredientID;
//
//    private String name;
//
//    private String category;
//
//    // Public no-args constructor
//    public Ingredient() {}
//
//    // Public custom constructor
//    public Ingredient(int ingredientID, String name, String category)
//    {
//        this.setIngredientID(ingredientID);
//        this.setName(name);
//        this.setCategory(category);
//    }
//
//    public int getIngredientID()
//    {
//        return ingredientID;
//    }
//
//    public void setIngredientID(int ingredientID)
//    {
//        this.ingredientID = ingredientID;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//
//    public String getCategory()
//    {
//        return category;
//    }
//
//    public void setCategory(String category)
//    {
//        this.category = category;
//    }
//}

//TODO: include category flag
public class Ingredient
{
    private String name;
    private int amount;
    private String measurement;
    private boolean selected;

    public Ingredient(String name, int amount, String measurement)
    {
        this.name = name;
        this.amount = amount;
        this.measurement = measurement;
        this.selected = false;
    }

    @Override
    public boolean equals(Object o)
    {
        return ((Ingredient) o).getName().equals(this.getName());
    }

    @Override
    public String toString()
    {
        return name;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public String getMeasurement()
    {
        return measurement;
    }

    public void setMeasurement(String measurement)
    {
        this.measurement = measurement;
    }
}
