package ke.co.keki.com.keki.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.List;

import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Ingredients;
import ke.co.keki.com.keki.utils.IngredientSharedPreference;
import ke.co.keki.com.keki.utils.PastryConstants;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    //List Of Ingredients
    public static String listOfIngredients;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PastryConstants.SHARED_PREFERENCE_KEY, 0);
        String recipeIngredientFromPreference = pref.getString(PastryConstants.PREFERENCE_RECIPE_NAME_KEY, context.getResources().getString(R.string.no_desired_ingredient));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_layout);
        //String Builder
        views.setTextViewText(R.id.tv_recipe_name_widget, context.getResources().getString(R.string.app_name));
        views.setTextViewText(R.id.tv_ingredients_widget, recipeIngredientFromPreference);

        StringBuilder stringBuilder = new StringBuilder();
        String recipeNameFromPreference = pref.getString(PastryConstants.PREFERENCE_RECIPE_NAME_KEY, context.getResources().getString(R.string.app_name));

        if (recipeNameFromPreference != null && !recipeNameFromPreference.equals("")) {
            List<Ingredients> ingredients = IngredientSharedPreference.getIngredientsAsList(context);
            if (ingredients != null) {
                for (int i = 0; i < ingredients.size(); i++) {
                    String ingredient = ingredients.get(i).getIngredientItem();
                    String measure = ingredients.get(i).getMeasure();
                    String quantity = String.valueOf(ingredients.get(i).getQuantity());

                    //Build An Ingredient Item
                    String ingredientItem = ingredient.concat(" " + quantity).concat(" " + measure).concat("\n");
                    //Append Each Ingredient
                    stringBuilder.append(ingredientItem);
                }
                Log.d("Ingredients List Final", stringBuilder.toString());
                listOfIngredients = stringBuilder.toString();
                views.setTextViewText(R.id.tv_recipe_name_widget, recipeNameFromPreference);
                views.setTextViewText(R.id.tv_ingredients_widget, listOfIngredients);
            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        Toast.makeText(context, "Widget Updated", Toast.LENGTH_LONG).show();
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
