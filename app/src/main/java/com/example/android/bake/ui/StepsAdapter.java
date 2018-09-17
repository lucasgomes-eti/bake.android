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
import com.example.android.bake.model.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public final ArrayList<Step> mSteps;
    final private ListItemClickListener mOnClickListener;

    public StepsAdapter(ArrayList<Step> mSteps, ListItemClickListener mOnClickListener) {
        this.mSteps = mSteps;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StepsViewHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_step_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Step step = mSteps.get(viewHolder.getAdapterPosition());

        ((StepsViewHolder) viewHolder).stepNumber.setText(String.valueOf(viewHolder.getAdapterPosition() + 1));
        ((StepsViewHolder) viewHolder).stepShortDescription.setText(step.getShortDescription());

        if (step.isSelected()) {
            ((StepsViewHolder) viewHolder).cardView.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorPrimary));
            ((StepsViewHolder) viewHolder).stepNumber.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.white));
            ((StepsViewHolder) viewHolder).stepShortDescription.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.white));
        } else {
            ((StepsViewHolder) viewHolder).cardView.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.white));
            ((StepsViewHolder) viewHolder).stepNumber.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorPrimary));
            ((StepsViewHolder) viewHolder).stepShortDescription.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), android.R.color.primary_text_light));
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
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
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
