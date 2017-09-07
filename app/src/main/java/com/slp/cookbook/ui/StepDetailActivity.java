package com.slp.cookbook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Steps;
import com.slp.cookbook.utils.CookBookConstants;

import java.util.List;

import butterknife.Bind;

public class StepDetailActivity extends AppCompatActivity implements CookBookConstants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        List<Steps> steps = getIntent().getParcelableArrayListExtra(STEPS);
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setPosition(getIntent().getExtras().getInt(POSITION));
        stepDetailFragment.setSteps(steps);

        getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, stepDetailFragment).commit();
    }

}
