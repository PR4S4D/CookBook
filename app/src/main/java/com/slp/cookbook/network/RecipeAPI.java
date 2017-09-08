package com.slp.cookbook.network;

import com.slp.cookbook.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lakshmiprasad on 06-09-2017.
 */

public interface RecipeAPI {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
