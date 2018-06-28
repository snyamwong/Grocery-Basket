package edu.wit.mobileapp.mealprepplanner;

public class Ingredient {
    private String name;
    private int amount;
    private String measurement;
    private String category;
    private boolean selected;

    public Ingredient(String name, int amount, String measurement, String category) {
        this.name = name;
        this.amount = amount;
        this.measurement = measurement;
        this.category = category;
        this.selected = false;
    }

    @Override
    public boolean equals(Object o) { return ((Ingredient)o).getName().equals(this.getName()); }

    @Override
    public String toString(){return name;}

    public boolean isSelected(){return selected;}

    public void setSelected(boolean selected){this.selected = selected;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
}
