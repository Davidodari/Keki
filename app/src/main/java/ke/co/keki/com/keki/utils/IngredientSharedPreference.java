package ke.co.keki.com.keki.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ke.co.keki.com.keki.model.pojo.Ingredients;

public class IngredientSharedPreference {

    public static List<Ingredients> ingredients;

    public static SharedPreferences.Editor initialisePreference(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PastryConstants.SHARED_PREFERENCE_KEY, 0); // 0 - for private mode
        return pref.edit();
    }

    public static void setRecipeNamePreference(String recipeName, Context context) {
        SharedPreferences.Editor editor = initialisePreference(context);
        editor.putString(PastryConstants.PREFERENCE_RECIPE_NAME_KEY, recipeName);
        editor.commit();
    }

    public static <T> void setIngredientListPreference(Context context, List<T> recipeIngredients) {
        SharedPreferences.Editor editor = initialisePreference(context);
        Gson gson = new Gson();
        // Convert List To String and store in shared preference
        String json = gson.toJson(recipeIngredients);
        Log.d("Json Output", " ".concat(json));
        editor.putString(PastryConstants.PREFERENCE_RECIPE_INGREDIENTS_KEY, json);
        editor.commit();
    }

    public static List<Ingredients> getIngredientsAsList(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PastryConstants.SHARED_PREFERENCE_KEY, 0);
        String serializedObject = pref.getString(PastryConstants.PREFERENCE_RECIPE_INGREDIENTS_KEY, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Ingredients>>() {
            }.getType();
            ingredients = gson.fromJson(serializedObject, type);
        }
        return ingredients;
    }

    public static void removePreferences(Context context) {
        SharedPreferences.Editor editor = initialisePreference(context);
        editor.remove(PastryConstants.PREFERENCE_RECIPE_NAME_KEY);
        editor.remove(PastryConstants.PREFERENCE_RECIPE_INGREDIENTS_KEY);
        editor.commit();
    }


}
