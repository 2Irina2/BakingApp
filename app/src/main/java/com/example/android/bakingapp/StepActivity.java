package com.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Fragments.StepFragment;

public class StepActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private StepFragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("recipe");
        setTitle(mRecipe.getName());

        if(savedInstanceState == null){
            stepFragment = new StepFragment();
            stepFragment.setRecipe(mRecipe);
            stepFragment.setIndex(intent.getIntExtra("index", 0));
        } else {
            stepFragment = (StepFragment) getSupportFragmentManager().getFragment(savedInstanceState, "stepFragment");
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_step, stepFragment)
                .commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            actionBar.hide();
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            actionBar.show();
        }
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "stepFragment", stepFragment);
    }
}
