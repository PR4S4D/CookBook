package com.slp.cookbook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.data.Steps;
import com.slp.cookbook.utils.CookBookConstants;

public class RecipeActivity extends AppCompatActivity implements CookBookConstants, RecipeStepsFragment.OnClickListener {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = getIntent().getParcelableExtra(RECIPE);
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setRecipeSteps(recipe.getSteps());
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_steps, recipeStepsFragment).commit();
    }

    public void showIngredients() {

    }

    @Override
    public void onClick(int position) {
        Log.i("onClick: ",recipe.getSteps().get(position).getDescription());;
    }
}
