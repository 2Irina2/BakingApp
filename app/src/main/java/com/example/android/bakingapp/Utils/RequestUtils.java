package com.example.android.bakingapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.LinearLayout;

import com.example.android.bakingapp.Classes.Ingredient;
import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Classes.Step;
import com.example.android.bakingapp.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestUtils {

    public static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String makeHttpRequest(String stringUrl) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(stringUrl)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public static List<Recipe> parseRecipeJson(String stringJSON) throws JSONException{
        List<Recipe> finalRecipeList = new ArrayList<Recipe>();

        JSONArray root = new JSONArray(stringJSON);

        for(int i = 0; i < root.length(); i++){
            JSONObject jsonRecipe = root.getJSONObject(i);

            String recipeName = "";
            if(jsonRecipe.has("name") && !jsonRecipe.getString("name").isEmpty()){
                recipeName = jsonRecipe.getString("name");
            }
            int recipeServings = -1;
            if(jsonRecipe.has("servings") && jsonRecipe.getInt("servings") != 0){
                recipeServings = jsonRecipe.getInt("servings");
            }
            String recipeImage = "";
            if(jsonRecipe.has("image") && !jsonRecipe.getString("image").isEmpty()){
                recipeImage = jsonRecipe.getString("image");
            }

            List<Ingredient> recipeIngredients = new ArrayList<Ingredient>();
            if(jsonRecipe.has("ingredients") && jsonRecipe.getJSONArray("ingredients") != null
                    && jsonRecipe.getJSONArray("ingredients").length() != 0){
                JSONArray jsonRecipeIngredients = jsonRecipe.getJSONArray("ingredients");

                for(int j = 0; j < jsonRecipeIngredients.length(); j++){
                    JSONObject jsonIngredient = jsonRecipeIngredients.getJSONObject(j);

                    String ingredientName = "";
                    if(jsonIngredient.has("ingredient") && !jsonIngredient.getString("ingredient").isEmpty()){
                        ingredientName = jsonIngredient.getString("ingredient");
                    }
                    int ingredientQuantity = -1;
                    if(jsonIngredient.has("quantity") && jsonIngredient.getInt("quantity") != 0){
                        ingredientQuantity = jsonIngredient.getInt("quantity");
                    }
                    String ingredientMeasure = "";
                    if(jsonIngredient.has("measure") && !jsonIngredient.getString("measure").isEmpty()){
                        ingredientMeasure = jsonIngredient.getString("measure");
                    }

                    Ingredient ingredient = new Ingredient(ingredientQuantity, ingredientMeasure, ingredientName);
                    recipeIngredients.add(ingredient);
                }
            }

            List<Step> recipeSteps = new ArrayList<Step>();
            if(jsonRecipe.has("steps") && jsonRecipe.getJSONArray("steps") != null
                    && jsonRecipe.getJSONArray("steps").length() != 0){
                JSONArray jsonRecipeSteps = jsonRecipe.getJSONArray("steps");

                for(int j = 0; j < jsonRecipeSteps.length(); j++){
                    JSONObject jsonStep = jsonRecipeSteps.getJSONObject(j);

                    String stepShortDescription = "";
                    if(jsonStep.has("shortDescription") && !jsonStep.getString("shortDescription").isEmpty()){
                        stepShortDescription = jsonStep.getString("shortDescription");
                    }
                    String stepLongDescription = "";
                    if(jsonStep.has("description") && !jsonStep.getString("description").isEmpty()){
                        stepLongDescription = jsonStep.getString("description");
                    }
                    String stepVideoUrl = "";
                    if(jsonStep.has("videoURL") && !jsonStep.getString("videoURL").isEmpty()){
                        stepVideoUrl = jsonStep.getString("videoURL");
                    }
                    String stepThumbnailURL = "";
                    if(jsonStep.has("thumbnailURL") && !jsonStep.getString("thumbnailURL").isEmpty()){
                        stepThumbnailURL = jsonStep.getString("thumbnailURL");
                    }

                    Step step = new Step(stepShortDescription, stepLongDescription, stepVideoUrl, stepThumbnailURL);
                    recipeSteps.add(step);
                }
            }

            Recipe recipe = new Recipe(recipeName, recipeServings, recipeImage, recipeSteps, recipeIngredients);
            finalRecipeList.add(recipe);
        }

        return finalRecipeList;
    }

    public static boolean hasInternetAccess(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

}
