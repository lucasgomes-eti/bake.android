package com.example.android.bake.ui;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bake.R;
import com.example.android.bake.model.Recipe;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final ArrayList<Recipe> mRecipes;
    final private ListItemClickListener mOnClickListener;

    public RecipesAdapter(ArrayList<Recipe> mRecipes, ListItemClickListener mOnClickListener) {
        this.mRecipes = mRecipes;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecipesViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recipe_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Recipe recipe = mRecipes.get(viewHolder.getAdapterPosition());

        ((RecipesViewHolder) viewHolder).recipeImage
                .setImageDrawable(ContextCompat
                        .getDrawable(viewHolder.itemView.getContext(), recipe.getImageResId()));

        ((RecipesViewHolder) viewHolder).recipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    private class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView recipeImage;
        final TextView recipeName;

        RecipesViewHolder(View itemView) {
            super(itemView);
            this.recipeImage = itemView.findViewById(R.id.iv_recipe);
            this.recipeName = itemView.findViewById(R.id.tv_recipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
