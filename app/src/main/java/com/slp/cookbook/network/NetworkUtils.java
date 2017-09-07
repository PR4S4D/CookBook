package com.slp.cookbook.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.ui.MainActivity;
import com.slp.cookbook.utils.CookBookConstants;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lakshmiprasad on 06-09-2017.
 */

public class NetworkUtils implements CookBookConstants {

    public static void getRecipes(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPES_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);
        Call<List<Recipe>> recipeList = recipeAPI.getRecipes();
        recipeList.enqueue((Callback<List<Recipe>>) context);
    }

}
