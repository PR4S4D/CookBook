package com.slp.cookbook.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.data.Steps;
import com.slp.cookbook.utils.CookBookConstants;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements CookBookConstants, RecipeStepsFragment.OnClickListener {

    private Recipe recipe;
    public static final String APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipe = getIntent().getParcelableExtra(RECIPE);
        setTitle(recipe.getName());
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setRecipeSteps(recipe.getSteps());
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_steps, recipeStepsFragment).commit();
        updateSharedPreferences();
    }

    private void updateSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences.Editor prefEditor = getSharedPreferences(COOKBOOK, MODE_PRIVATE).edit();
        prefEditor.putString(RECIPE, gson.toJson(recipe));
        prefEditor.commit();

        Intent intent = new Intent(APPWIDGET_UPDATE).setPackage(getPackageName());
        getApplicationContext().sendBroadcast(intent);
    }

    public void showIngredients(View view){
        Intent intent = new Intent(this, IngredientActivity.class);
        intent.putExtra(RECIPE,recipe);
        startActivity(intent);

    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putParcelableArrayListExtra(STEPS, (ArrayList<? extends Parcelable>) recipe.getSteps());
        intent.putExtra(POSITION, position);
        startActivity(intent);
    }
}
