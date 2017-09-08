package com.slp.cookbook.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.slp.cookbook.R;
import com.slp.cookbook.ui.MainActivity;
import com.slp.cookbook.ui.RecipeActivity;
import com.slp.cookbook.utils.CookBookConstants;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeStepsWidget extends AppWidgetProvider implements CookBookConstants {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.recipe_steps_widget);
        Intent intent = new Intent(context,RecipeWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.recipe_steps_lv,intent);
        remoteViews.setEmptyView(R.id.recipe_steps_lv,R.id.empty);

        Intent mainActivity = new Intent(context, MainActivity.class);
        PendingIntent clickPendingIntentTemplate = PendingIntent.getActivity(context,0,mainActivity,0);
        remoteViews.setOnClickPendingIntent(R.id.recipe_title,clickPendingIntentTemplate);


        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(RecipeActivity.APPWIDGET_UPDATE == intent.getAction()){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.recipe_steps_lv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.recipe_title);
        }
     }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

