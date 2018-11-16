package ke.co.keki.com.keki.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public abstract class PastryConstants {
    //Constant Key Names Used to Pass Data betweem Activities and Fragments
    public static final String PASTRY = "pastry";
    public static final String PASTRY_LIST = "pastry_list";
    public static final String STEPS_LIST = "steps_list";
    public static final String STEPS_OBJECT = "steps_object";
    public static final String STEPS_ID = "steps id";
    public static final String VIDEO_URL = "video_link";
    public static final String CURRENT_STEP = "current_step";
    public static final String STEP_DESCRIPTION = "step_description";

    //Shared Preference
    public static final String SHARED_PREFERENCE_KEY = "shared_preferences";
    public static final String PREFERENCE_RECIPE_NAME_KEY = "shared_preferences_name";
    public static final String PREFERENCE_RECIPE_INGREDIENTS_KEY = "shared_preferences_ingredients";



    /**
     * @param context Recycler view instance
     * @return number of columns for span count
     */
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scaleFactor = 200;
        int noOfColumns = (int) (dpWidth / scaleFactor);
        if (noOfColumns == 3)
            noOfColumns = 2;
        return noOfColumns;
    }
}
