package com.example.android.bake.ui;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bake.R;
import com.example.android.bake.model.Ingredient;
import com.example.android.bake.model.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public final ArrayList<Step> mSteps;
    public final ArrayList<Ingredient> mIngredients;
    final private ListItemClickListener mOnClickListener;

    private static final int STEP_TYPE = 0;
    private static final int INGREDIENT_TYPE = 1;

    StepsAdapter(ArrayList<Step> steps, ArrayList<Ingredient> ingredients, ListItemClickListener mOnClickListener) {
        this.mSteps = steps;
        this.mIngredients = ingredients;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return INGREDIENT_TYPE;
        } else {
            return STEP_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == STEP_TYPE) {
            return new StepsViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_step_content, viewGroup, false));
        } else {
            return new IngredientsViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_ingredients, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof StepsViewHolder) {
            Step step = mSteps.get(viewHolder.getAdapterPosition() - 1);

            StepsViewHolder stepsViewHolder = (StepsViewHolder) viewHolder;

            stepsViewHolder.stepNumber.setText(String.valueOf(viewHolder.getAdapterPosition()));
            stepsViewHolder.stepShortDescription.setText(step.getShortDescription());

            if (step.isSelected()) {
                stepsViewHolder.cardView.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorPrimary));
                stepsViewHolder.stepNumber.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.white));
                stepsViewHolder.stepShortDescription.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.white));
            } else {
                stepsViewHolder.cardView.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.white));
                stepsViewHolder.stepNumber.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorPrimary));
                stepsViewHolder.stepShortDescription.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.primary_text_light));
            }
        } else if (viewHolder instanceof IngredientsViewHolder) {
            IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) viewHolder;

            for (Ingredient mIngredient : mIngredients) {
                ingredientsViewHolder.textIngredients.append(mIngredient.getQuantity() + " " + mIngredient.getMeasure() + " " + mIngredient.getIngredient() + "\n");
            }
        }

    }

    @Override
    public int getItemCount() {
        return mSteps.size() + 1;
    }

    private class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView stepShortDescription;
        TextView stepNumber;

        StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.cv_step);
            this.stepShortDescription = itemView.findViewById(R.id.tv_step_short_description);
            this.stepNumber = itemView.findViewById(R.id.tv_step_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition() - 1);
        }
    }

    private class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView textIngredients;

        IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textIngredients = itemView.findViewById(R.id.tv_ingredients);
        }
    }
}
