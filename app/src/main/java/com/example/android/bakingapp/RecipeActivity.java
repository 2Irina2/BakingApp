package com.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Fragments.MasterFragment;
import com.example.android.bakingapp.Fragments.StepFragment;

import java.util.List;

public class RecipeActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    private Recipe mRecipe;
    private int mIndex = 0;
    private Fragment currentFragment;
    private StepFragment stepFragment;
    private MasterFragment masterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("recipe");
        setTitle(mRecipe.getName());

        masterFragment = new MasterFragment();
        masterFragment.setRecipe(mRecipe);
        stepFragment = new StepFragment();
        stepFragment.setRecipe(mRecipe);

        if (savedInstanceState != null) {
            currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout, masterFragment)
                    .commit();
            currentFragment = masterFragment;
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, currentFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (currentFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "currentFragment", currentFragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                navigateBackToCorrectScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        navigateBackToCorrectScreen();

    }

    @Override
    public void onItemClick(View v, int position) {
        Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();

        stepFragment.setIndex(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, stepFragment)
                .addToBackStack("masterFragment")
                .commit();
        currentFragment = stepFragment;
    }

    private void navigateBackToCorrectScreen() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() != 1) {
            //Toast.makeText(getApplicationContext(), "Fragments size = " + Integer.toString(fragments.size()), Toast.LENGTH_SHORT).show();
        } else if (fragments.size() == 1) {
            if (fragments.get(0) == masterFragment) {
                //Toast.makeText(getApplicationContext(), "F size is 1: masterFragment", Toast.LENGTH_SHORT).show();
                NavUtils.navigateUpFromSameTask(this);
            } else if (fragments.get(0) == stepFragment) {
                getSupportFragmentManager().popBackStack("masterFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //Toast.makeText(getApplicationContext(), "F size is 1: stepFragment", Toast.LENGTH_SHORT).show();
                currentFragment = masterFragment;
            }
        }
    }
}
