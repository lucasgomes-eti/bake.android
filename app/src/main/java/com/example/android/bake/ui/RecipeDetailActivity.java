package com.example.android.bake.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.example.android.bake.R;
import com.example.android.bake.model.Recipe;
import com.example.android.bake.model.Step;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements ListItemClickListener {

    private Boolean twoPane = false;

    private StepsAdapter stepsAdapter = new StepsAdapter(new ArrayList<>(), this);
    private RecyclerView.LayoutManager layoutManager;

    public static String STEP_KEY = "step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe recipe = getIntent().getParcelableExtra(RecipesActivity.RECIPE_KEY);

        setTitle(recipe.getName());

        RecyclerView itemStepList = findViewById(R.id.item_step_list);
        FrameLayout itemStepDetailContainer = findViewById(R.id.item_step_detail_container);

        layoutManager = new LinearLayoutManager(this);

        itemStepList.setLayoutManager(layoutManager);
        itemStepList.setAdapter(stepsAdapter);

        stepsAdapter.mSteps.addAll(recipe.getSteps());
        stepsAdapter.notifyDataSetChanged();

        if (itemStepDetailContainer != null) {
            twoPane = true;

            Step step = stepsAdapter.mSteps.get(0);
            step.setSelected(true);

            StepFragment stepFragment = StepFragment.newInstance(step);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_step_detail_container, stepFragment)
                    .commit();

            stepsAdapter.mSteps.set(0, step);
            stepsAdapter.notifyItemChanged(0);
        }
    }

    @Override
    public void onListItemClick(int itemIndex) {
        Step step = stepsAdapter.mSteps.get(itemIndex);

        if (twoPane) {
            StepFragment stepFragment = StepFragment.newInstance(step);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_step_detail_container, stepFragment)
                    .commit();

            for (int i = 0; i < stepsAdapter.mSteps.size(); i ++) {
                Step mStep = stepsAdapter.mSteps.get(i);
                if (mStep.isSelected()) {
                    mStep.setSelected(false);
                    stepsAdapter.mSteps.set(i, mStep);
                    stepsAdapter.notifyItemChanged(i);
                }
            }

            step.setSelected(true);
            stepsAdapter.mSteps.set(itemIndex, step);
            stepsAdapter.notifyItemChanged(itemIndex);
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(STEP_KEY, step);
            startActivity(intent);
        }
    }
}
