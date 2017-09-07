package com.slp.cookbook.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.utils.CookBookConstants;


/**
 * Created by Lakshmiprasad on 07-09-2017.
 */

public class RecipeWidgetFactory implements RemoteViewsService.RemoteViewsFactory, CookBookConstants {

    private Context context;
    private Intent intent;
    private Recipe recipe;

    public RecipeWidgetFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        String recipeString = context.getSharedPreferences(COOKBOOK, Context.MODE_PRIVATE).getString(RECIPE, null);
        if (null != recipeString) {
            recipe = new Gson().fromJson(recipeString, Recipe.class);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {


        if (null != recipe)
            return recipe.getSteps().size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_item);
        remoteViews.setTextViewText(R.id.recipe_step_widget, recipe.getSteps().get(i).getShortDescription());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(context.getPackageName(), R.layout.widget_recipe_item);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
