package com.slp.cookbook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.network.RecipeAPI;
import com.slp.cookbook.utils.CookBookConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CookBookConstants, Callback<List<Recipe>> {

    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipes = getRecipes();
    }

    public List<Recipe> getRecipes() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPES_END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);

        Call<List<Recipe>> recipeList = recipeAPI.getRecipes();
        recipeList.enqueue(MainActivity.this);
        return recipes;
    }


    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        Log.i("Retrofit ", "onResponse: "+response.body());
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {

    }
}
