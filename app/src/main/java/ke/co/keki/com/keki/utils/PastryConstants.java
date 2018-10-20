package ke.co.keki.com.keki.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public abstract class PastryConstants {
    public static String PASTRY = "pastry";
    public static String PASTRY_LIST = "pastry_list";
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
