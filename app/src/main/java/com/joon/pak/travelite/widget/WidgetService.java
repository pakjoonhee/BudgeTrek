package com.joon.pak.travelite.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by joonheepak on 10/17/16.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
