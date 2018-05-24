package com.example.android.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Utils.RequestUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    private static final String TAG = ListActivity.class.getName();

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> finalRecipeList = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recipe_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if(RequestUtils.hasInternetAccess(this) && finalRecipeList.size() == 0){
            new RecipeQueryTask().execute(RequestUtils.REQUEST_URL);
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        //Toast.makeText(getApplicationContext(), recipeAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
        intent.putExtra("recipe", recipeAdapter.getItem(position));
        startActivity(intent);
    }


    public class RecipeQueryTask extends AsyncTask<String, Void, List<Recipe>>{

        @Override
        protected List<Recipe> doInBackground(String... strings) {

            List<Recipe> recipeList = new ArrayList<Recipe>();

            String jsonResp = "";
            try{
                jsonResp = RequestUtils.makeHttpRequest(RequestUtils.REQUEST_URL);
                recipeList = RequestUtils.parseRecipeJson(jsonResp);
            } catch (IOException e){
                Log.e(TAG, "Error making http request");
            } catch (JSONException e){
                Log.e(TAG, "Error parsing JSON");
            }

            Log.e(TAG, "Successfully completed network request and data parsing");
            finalRecipeList = recipeList;
            return recipeList;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeList, ListActivity.this);
            recyclerView.setAdapter(recipeAdapter);
        }
    }
}
