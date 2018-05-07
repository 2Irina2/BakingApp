package com.example.android.bakingapp.Classes;

public class Ingredient {

    private int mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient(int quantity, String measure, String ingredient){
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    public int getQuantity(){
        return mQuantity;
    }

    public String getMeasure(){
        return mMeasure;
    }

    public String getIngredient(){
        return mIngredient;
    }
}
