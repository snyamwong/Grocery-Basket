package edu.wit.mobileapp.mealprepplanner;

public class Ingredient {
    private String name;
    private int amount;
    private String measurement;

    public Ingredient(String name, int amount, String measurement) {
        this.name = name;
        this.amount = amount;
        this.measurement = measurement;
    }

    @Override
    public boolean equals(Object o) {
        if(((Ingredient)o).getName().equals(this.getName())){
            return true;
        }else{
            return false;
        }
    }

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
}
