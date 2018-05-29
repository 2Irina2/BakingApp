package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Fragments.MasterFragment;
import com.example.android.bakingapp.Fragments.StepFragment;
import com.example.android.bakingapp.Utils.RecyclerViewItemClickListener;
import com.example.android.bakingapp.Widget.RecipeWidgetProvider;

public class RecipeActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    private boolean mTwoPane;

    private Recipe mRecipe;
    private MasterFragment masterFragment;
    private StepFragment stepFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intentFromList = getIntent();
        mRecipe = intentFromList.getParcelableExtra("recipe");
        setTitle(mRecipe.getName());

        masterFragment = new MasterFragment();
        masterFragment.setRecipe(mRecipe);

        if(findViewById(R.id.step_fragment) != null){
            mTwoPane = true;

            if(savedInstanceState == null){
                FragmentManager fragmentManager = getSupportFragmentManager();

                stepFragment = new StepFragment();
                stepFragment.setRecipe(mRecipe);
                stepFragment.setIndex(0);
                fragmentManager.beginTransaction()
                        .add(R.id.step_fragment, stepFragment)
                        .add(R.id.master_list_fragment, masterFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout, masterFragment)
                    .commit();
        }

        Intent widgetIntent = new Intent(this, RecipeWidgetProvider.class);
        widgetIntent.setAction(RecipeWidgetProvider.ACTION_UPDATE_INGREDIENTS);
        widgetIntent.putExtra("recipe", mRecipe);
        int ids[] = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(widgetIntent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(View v, int position) {

        if(mTwoPane){
            StepFragment newFragment = new StepFragment();
            newFragment.setRecipe(mRecipe);
            newFragment.setIndex(position);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_fragment, newFragment)
                    .commit();
        } else {
            Intent stepIntent = new Intent(this, StepActivity.class);
            stepIntent.putExtra("recipe", mRecipe);
            stepIntent.putExtra("index", position);
            startActivity(stepIntent);
        }
    }
}
