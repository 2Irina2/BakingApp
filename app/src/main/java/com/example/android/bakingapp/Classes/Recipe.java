package com.example.android.bakingapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable{

    private String mName;
    private int mServings;
    private String mImageUrl;
    private List<Step> mSteps = new ArrayList<Step>();
    private List<Ingredient> mIngredients = new ArrayList<Ingredient>();

    public Recipe(String name, int servings, String imageUrl, List<Step> steps, List<Ingredient> ingredients){
        mName = name;
        mServings = servings;
        mImageUrl = imageUrl;
        mSteps = steps;
        mIngredients = ingredients;
    }

    protected Recipe(Parcel in) {
        mName = in.readString();
        mServings = in.readInt();
        mImageUrl = in.readString();
        in.readTypedList(mSteps, Step.CREATOR);
        in.readTypedList(mIngredients, Ingredient.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mServings);
        parcel.writeString(mImageUrl);
        parcel.writeTypedList(mSteps);
        parcel.writeTypedList(mIngredients);
    }
}
