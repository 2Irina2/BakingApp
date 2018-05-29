package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Utils.RecyclerViewItemClickListener;
import com.example.android.bakingapp.Utils.RequestUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListActivity extends AppCompatActivity implements RecyclerViewItemClickListener,
        LoaderManager.LoaderCallbacks<List<Recipe>>{

    private static final String TAG = ListActivity.class.getName();
    private static final int RECIPE_LOADER_ID = 0;

    private RecipeAdapter recipeAdapter;
    private static List<Recipe> finalRecipeList = new ArrayList<Recipe>();
    private RecyclerView recyclerView;
    @BindView(R.id.error_message) LinearLayout errorDisplayLinearLayout;
    @BindView(R.id.loading_indicator) ProgressBar loadingIndicatorProgressBar;
    @BindView(R.id.error_message_tv) TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(findViewById(R.id.recipe_list_rv_tablet) != null){
            recyclerView = findViewById(R.id.recipe_list_rv_tablet);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView = findViewById(R.id.recipe_list_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        errorDisplayLinearLayout = findViewById(R.id.error_message);
        loadingIndicatorProgressBar = findViewById(R.id.loading_indicator);
        errorTv = findViewById(R.id.error_message_tv);

        if(finalRecipeList.size() == 0){
            if(RequestUtils.hasInternetAccess(this)){
                getSupportLoaderManager().initLoader(RECIPE_LOADER_ID, null, ListActivity.this);
                errorDisplayLinearLayout.setVisibility(View.INVISIBLE);
                loadingIndicatorProgressBar.setVisibility(View.VISIBLE);
            } else {
                errorDisplayLinearLayout.setVisibility(View.VISIBLE);
                errorTv.setText(getResources().getString(R.string.error_internet_connection));
            }
        } else {
            errorDisplayLinearLayout.setVisibility(View.INVISIBLE);
            loadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
            recipeAdapter = new RecipeAdapter(getApplicationContext(), finalRecipeList, ListActivity.this);
            recyclerView.setAdapter(recipeAdapter);
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        //Toast.makeText(getApplicationContext(), recipeAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
        intent.putExtra("recipe", recipeAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeQueryLoader(this);

    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        recipeAdapter = new RecipeAdapter(getApplicationContext(), data, ListActivity.this);
        recyclerView.setAdapter(recipeAdapter);
        loadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }


    public static class RecipeQueryLoader extends AsyncTaskLoader<List<Recipe>> {
        public RecipeQueryLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            forceLoad();

        }

        @Override
        public List<Recipe> loadInBackground() {
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
    }
}
