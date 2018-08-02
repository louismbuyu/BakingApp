package com.example.louisnelsonlevoride.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import static com.example.louisnelsonlevoride.bakingapp.Recipe.RecipeViewHolder.FAVOURITE;
import static com.example.louisnelsonlevoride.bakingapp.Recipe.RecipeViewHolder.MyPREFERENCES;

public class BakingAppWidget extends AppWidgetProvider {
    SharedPreferences sharedpreferences;
    private static String TAG = "BakingAppWidget";
    private AppWidgetManager appWidgetManagerFromWidget;
    private int[] appWidgetIdsFromWidget;
    private Context appWidgetContext;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        CharSequence widgetText = "Select your favourite recipe";//context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String favouriteIngredients = sharedpreferences.getString(FAVOURITE, null);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

        if (favouriteIngredients != null){
            if (favouriteIngredients.isEmpty() == false){
                views.setTextViewText(R.id.appwidget_text, favouriteIngredients);
            }else{
                views.setTextViewText(R.id.appwidget_text, widgetText);
            }
        }else{
            views.setTextViewText(R.id.appwidget_text, widgetText);
        }
        //Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        appWidgetManagerFromWidget = appWidgetManager;
        appWidgetIdsFromWidget = appWidgetIds;
        appWidgetContext = context;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void triggerUpdate(){

    }

    public AppWidgetManager getAppWidgetManager(){
        return appWidgetManagerFromWidget;
    }

    public int[] getAppWidgetIds(){
        return appWidgetIdsFromWidget;
    }

    public Context getContext(){
        return appWidgetContext;
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
