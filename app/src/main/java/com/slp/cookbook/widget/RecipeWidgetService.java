package com.slp.cookbook.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Lakshmiprasad on 07-09-2017.
 */

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetFactory(getApplicationContext(),intent);
    }
}
