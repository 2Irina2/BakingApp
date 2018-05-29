package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Classes.Ingredient;
import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Utils.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Locale;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipeList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewItemClickListener mRecipeItemClickListener;

    public RecipeAdapter(Context context, List<Recipe> recipes, RecyclerViewItemClickListener recipeItemClickListener){
        mContext = context;
        mRecipeList = recipes;
        mLayoutInflater = LayoutInflater.from(mContext);
        mRecipeItemClickListener = recipeItemClickListener;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.recipeServings.setText(String.format(Locale.getDefault(),"Servings: %d", recipe.getServings()));
        holder.recipeIngredients.setText(String.format(Locale.getDefault(), "Ingredients: %s", recipe.getIngredientsChunk()));
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public Recipe getItem(int position){
        return mRecipeList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeName;
        TextView recipeServings;
        TextView recipeIngredients;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_item_name);
            recipeServings = itemView.findViewById(R.id.recipe_item_servings);
            recipeIngredients = itemView.findViewById(R.id.recipe_item_ingredients);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mRecipeItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
