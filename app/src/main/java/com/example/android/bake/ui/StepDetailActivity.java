package com.example.android.bake.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bake.R;
import com.example.android.bake.model.Step;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Step step = getIntent().getParcelableExtra(RecipeDetailActivity.STEP_KEY);

        setTitle(step.getShortDescription());

        if (savedInstanceState == null) {
            StepFragment stepFragment = StepFragment.newInstance(step);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_step_detail_container, stepFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
