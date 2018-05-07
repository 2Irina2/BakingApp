package com.example.android.bakingapp.Classes;

import java.util.List;

public class Recipe {

    private String mName;
    private int mServings;
    private String mImageUrl;
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;

    public Recipe(String name, int servings, String imageUrl, List<Step> steps, List<Ingredient> ingredients){
        mName = name;
        mServings = servings;
        mImageUrl = imageUrl;
        mSteps = steps;
        mIngredients = ingredients;
    }

    public String getName(){
        return mName;
    }

    public String getmImageUrl(){
        return mImageUrl;
    }

    public int getServings(){
        return mServings;
    }

    public List<Step> getSteps(){
        return mSteps;
    }

    public List<Ingredient> getIngredients(){
        return mIngredients;
    }
}
