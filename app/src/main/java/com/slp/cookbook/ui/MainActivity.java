package com.slp.cookbook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.network.NetworkUtils;
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
        NetworkUtils.getRecipes(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        recipes = response.body();
        for(Recipe recipe : recipes){
            Log.i("Recipes",recipe.getName());
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Toast.makeText(this,getString(R.string.failure_message),Toast.LENGTH_LONG).show();
    }
}
