package ke.co.keki.com.keki.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ke.co.keki.com.keki.model.pojo.Ingredients;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;


/**
 * Class parsing JSON
 *
 * @author David Odari
 */
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
    private final static String STEPS_SHORT_DESCRIPTION = "shortDescription";
    private final static String STEPS_DESCRIPTION = "description";
    private final static String STEPS_VIDEO_URL = "videoURL";
    private final static String STEPS_THUMBNAIL_URL = "thumbnailURL";

    private static Pastry pastryObject;
    private static List<Ingredients> ingredientsList;
    private static List<Steps> stepsList;
    private static List<Pastry> pastries;


    /**
     * Parsing of jsonobjects to Java Objects
     *
     * @param jsonResponse raw json response as a string which will be parsed
     * @return list of pastries parsed from the JSON RESPONSE
     */
    public static List<Pastry> parseJSON(String jsonResponse) {
        ingredientsList = new ArrayList<>();
        stepsList = new ArrayList<>();
        List<Pastry> pastriesList = new ArrayList<>();

        try {
            JSONArray jsonArrayResponse = new JSONArray(jsonResponse);
            //no of elements = 4

            for (int i = 0; i < jsonArrayResponse.length(); i++) {
                //get each object piece by piece
                JSONObject pastriesObject = jsonArrayResponse.getJSONObject(i);
                //object properties
                int pastryId = pastriesObject.getInt(PASTRY_ID);
                String pastryName = pastriesObject.getString(PASTRY_NAME);
                int pastryServings = pastriesObject.getInt(PASTRY_SERVINGS);
                String pastryImage = pastriesObject.getString(PASTRY_IMAGE);

                //JSON ARRAYS
                JSONArray ingredients = pastriesObject.getJSONArray(PASTRY_INGREDIENTS);
                for (int ingredientItem = 0; ingredientItem < ingredients.length(); ingredientItem++) {
                    //get each ingredient one by one;
                    JSONObject ingredObject = ingredients.getJSONObject(ingredientItem);

                    int quantity;
                    String measure, item;
                    //ingredient item
                    quantity = ingredObject.getInt(INGREDIENT_QUANTITY);
                    measure = ingredObject.getString(INGREDIENT_MEASURE);
                    item = ingredObject.getString(INGREDIENT_ITEM);
                    //Add ingredient to list
                    ingredientsList.add(new Ingredients(quantity, measure, item));

                }
                JSONArray steps = pastriesObject.getJSONArray(PASTRY_STEPS);
                for (int stepsToFollow = 0; stepsToFollow < steps.length(); stepsToFollow++) {
                    JSONObject stepsObject = steps.getJSONObject(stepsToFollow);
                    int id;
                    String shortDescription, description, videoUrl, thumbnailUrl;

                    id = stepsObject.getInt(STEPS_ID);
                    shortDescription = stepsObject.getString(STEPS_SHORT_DESCRIPTION);
                    description = stepsObject.getString(STEPS_DESCRIPTION);
                    videoUrl = stepsObject.getString(STEPS_VIDEO_URL);
                    thumbnailUrl = stepsObject.getString(STEPS_THUMBNAIL_URL);
                    stepsList.add(new Steps(id, shortDescription, description, videoUrl, thumbnailUrl));
                }
                pastryObject = new Pastry(pastryId, pastryName, ingredientsList, stepsList, pastryServings, pastryImage);
                Log.d(TAG, "pastryName" + pastryObject.getName());
                pastriesList.add(pastryObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "jsonError");
        }
        Log.d(TAG, "pastryListSize:" + pastriesList.size());
        setPastriesList(pastriesList);
        return pastriesList;
    }

    private static void setPastriesList(List<Pastry> pastries) {
        JsonUtils.pastries = pastries;
    }

    public static List<Pastry> getPastries() {
        return pastries;
    }
}
