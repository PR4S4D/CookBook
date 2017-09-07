package com.slp.cookbook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.utils.CookBookConstants;
import com.slp.cookbook.utils.RecipeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity implements CookBookConstants {

    Recipe recipe;

    @Bind(R.id.ingredients)
    ListView ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);
        recipe = getIntent().getParcelableExtra(RECIPE);
        setTitle(recipe.getName());
        ingredients.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, RecipeUtils.getIngredients(recipe.getIngredients())));

    }
}
