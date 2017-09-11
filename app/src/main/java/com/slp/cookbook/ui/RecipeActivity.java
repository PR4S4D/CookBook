package com.slp.cookbook.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.utils.CookBookConstants;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements CookBookConstants, RecipeStepsFragment.OnClickListener {

    private Recipe recipe;
    public static final String APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";

    private FrameLayout recipeStepDetailContainer;
    private Boolean twoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeStepDetailContainer = (FrameLayout) findViewById(R.id.recipe_step_detail_container);
        recipe = getIntent().getParcelableExtra(RECIPE);
        setTitle(recipe.getName());
        updateSharedPreferences();
        twoPane = getResources().getBoolean(R.bool.isTablet);
        if (savedInstanceState == null)
            initializeFragment();


    }

    private void initializeFragment() {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setRecipeSteps(recipe.getSteps());
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_steps, recipeStepsFragment).commit();
    }

    private void updateSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences.Editor prefEditor = getSharedPreferences(COOKBOOK, MODE_PRIVATE).edit();
        prefEditor.putString(RECIPE, gson.toJson(recipe));
        prefEditor.apply();

        Intent intent = new Intent(APPWIDGET_UPDATE).setPackage(getPackageName());
        getApplicationContext().sendBroadcast(intent);
    }

    public void showIngredients(View view) {
        if(twoPane){
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_step_detail_container, new IngredientFragment()).commit();
        }else {
            Intent intent = new Intent(this, IngredientActivity.class);
            intent.putExtra(RECIPE, recipe);
            startActivity(intent);
        }


    }

    @Override
    public void onClick(int position) {
        if (twoPane) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setPosition(position);
            stepDetailFragment.setSteps(recipe.getSteps());

            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_step_detail_container, stepDetailFragment).commit();
        } else {

            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putParcelableArrayListExtra(STEPS, (ArrayList<? extends Parcelable>) recipe.getSteps());
            intent.putExtra(POSITION, position);
            startActivity(intent);
        }
    }
}
