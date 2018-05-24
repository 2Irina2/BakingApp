package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.MasterRecyclerViewAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecyclerViewItemClickListener;

public class MasterFragment extends Fragment {

    private Recipe mRecipe;
    private RecyclerView mRecyclerView;
    private MasterRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerViewItemClickListener listener;

    public MasterFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (RecyclerViewItemClickListener) getContext();
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement RecyclerViewItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable("recipeMaster");
        }

        mRecyclerView = rootView.findViewById(R.id.fragment_master_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAdapter = new MasterRecyclerViewAdapter(getContext(), mRecipe.getSteps(), listener);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipeMaster", mRecipe);
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }

}
