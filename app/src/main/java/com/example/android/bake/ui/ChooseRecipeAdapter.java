package com.example.android.bake.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bake.R;
import com.example.android.bake.model.Recipe;

import java.util.ArrayList;

class ChooseRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final ArrayList<Recipe> mRecipes;
    final private ListItemClickListener mOnClickListener;

    public ChooseRecipeAdapter(ArrayList<Recipe> mRecipes, ListItemClickListener mOnClickListener) {
        this.mRecipes = mRecipes;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChooseRecipeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_widget, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof ChooseRecipeViewHolder) {
            Recipe recipe = mRecipes.get(viewHolder.getAdapterPosition());

            ChooseRecipeViewHolder chooseRecipeViewHolder = (ChooseRecipeViewHolder) viewHolder;

            chooseRecipeViewHolder.textItem.setText(recipe.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    private class ChooseRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textItem;

        ChooseRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textItem = itemView.findViewById(R.id.tv_item_widget);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
