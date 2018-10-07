package ke.co.keki.com.keki.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Ingredients;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    //JSON KEYS
    private final static String PASTRY_ID = "id";
    private final static String PASTRY_NAME = "name";
    private final static String PASTRY_INGREDIENTS = "ingredients";
    private final static String PASTRY_STEPS = "steps";
    private final static String PASTRY_SERVINGS = "servings";
    private final static String PASTRY_IMAGE = "image";

    //Ingredients JSON Array Keys
    private final static String INGREDIENT_QUANTITY = "quantity";
    private final static String INGREDIENT_MEASURE = "measure";
    private final static String INGREDIENT_ITEM = "ingredient";

    //Steps JSON Array
    private final static String STEPS_ID = "id";
    private final static String STEPS_SHORT_DESCRIPTION = "shortdescription";
    private final static String STEPS_DESCRIPTION = "description";
    private final static String STEPS_VIDEO_URL = "videoURL";
    private final static String STEPS_THUMBNAIL_URL = "thumbnailURL";

    private static Pastry pastryObject;
    private static Ingredients ingredient;
    private static List<Ingredients> ingredientsList;
    private static Steps step;
    private static List<Steps> stepsList;
    private static List<Pastry> pastriesList;
    private static Pastry pastry;


    public static List<Pastry> parseJSON(String jsonObject) {
        try {

            JSONObject pastryJson = new JSONObject(jsonObject);
            //Pastry Id
            int pastryId = pastryJson.getInt(PASTRY_ID);
            String pastryName = pastryJson.getString(PASTRY_NAME);
            int pastryServings = pastryJson.getInt(PASTRY_SERVINGS);
            String pastryImage = pastryJson.getString(PASTRY_IMAGE);

            //JSON ARRAYS
            JSONArray ingredients = pastryJson.getJSONArray(PASTRY_INGREDIENTS);
            JSONArray steps = pastryJson.getJSONArray(PASTRY_STEPS);

            for (int i = 0; i < ingredients.length(); i++) {
                JSONObject ingredObject = ingredients.getJSONObject(i);
                int quantity;
                String measure, item;
                quantity = ingredObject.getInt(INGREDIENT_QUANTITY);
                measure = ingredObject.getString(INGREDIENT_MEASURE);
                item = ingredObject.getString(INGREDIENT_ITEM);
                ingredient = new Ingredients(quantity, measure, item);
                ingredientsList.add(ingredient);
            }
            for (int i = 0; i < steps.length(); i++) {
                JSONObject stepsObject = ingredients.getJSONObject(i);
                int id;
                String shortDescription, description, videoUrl, thumbnailUrl;
                id = stepsObject.getInt(STEPS_ID);
                shortDescription = stepsObject.getString(STEPS_SHORT_DESCRIPTION);
                description = stepsObject.getString(STEPS_DESCRIPTION);
                videoUrl = stepsObject.getString(STEPS_VIDEO_URL);
                thumbnailUrl = stepsObject.getString(STEPS_THUMBNAIL_URL);
                step = new Steps(id, shortDescription, description, videoUrl, thumbnailUrl);
                stepsList.add(step);
            }

            pastryObject = new Pastry(pastryId, pastryName, ingredientsList, stepsList, pastryServings, pastryImage);
            pastriesList.add(pastryObject);
            Log.d(TAG, "pastryObject" + pastryObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "jsonError");
        }

        return pastriesList;
    }
}
