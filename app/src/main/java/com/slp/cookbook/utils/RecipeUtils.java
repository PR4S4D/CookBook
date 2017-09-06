package com.slp.cookbook.utils;

import com.slp.cookbook.data.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lakshmiprasad on 06-09-2017.
 */

public class RecipeUtils {

    public static List<String> getRecipeNames(List<Recipe> recipes){
        List<String> recipeNames = new ArrayList<>();
        for(Recipe recipe:recipes)
            recipeNames.add(recipe.getName());

        return recipeNames;
    }

}
