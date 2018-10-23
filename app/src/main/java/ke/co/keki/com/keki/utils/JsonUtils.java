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

    private static List<Pastry> pastries;


    /**
     * Parsing of jsonobjects to Java Objects
     *
     * @param jsonResponse raw json response as a string which will be parsed
     * @return list of pastries parsed from the JSON RESPONSE
     */
    public static List<Pastry> parseJSON(String jsonResponse) {


        //List of pastries
        List<Pastry> pastriesList = new ArrayList<>();

        try {
            JSONArray jsonArrayResponse = new JSONArray(jsonResponse);
            //no of elements = 4
            //for each object set its properties below and add object to list
            for (int i = 0; i < jsonArrayResponse.length(); i++) {
                Pastry pastryObject = new Pastry();
                //get each object piece by piece
                JSONObject pastriesObject = jsonArrayResponse.getJSONObject(i);
                //Pastry Object properties
                int pastryId = pastriesObject.getInt(PASTRY_ID);
                String pastryName = pastriesObject.getString(PASTRY_NAME);
                int pastryServings = pastriesObject.getInt(PASTRY_SERVINGS);
                String pastryImage = pastriesObject.getString(PASTRY_IMAGE);

                //Pastry Object Ingredients
                List<Ingredients> ingredientsList = new ArrayList<>();
                JSONArray ingredients = pastriesObject.getJSONArray(PASTRY_INGREDIENTS);
                for (int ingredientItem = 0; ingredientItem < ingredients.length(); ingredientItem++) {
                    //get each ingredient one by one;
                    JSONObject ingredObject = ingredients.getJSONObject(ingredientItem);
                    int quantity;
                    String measure, item;
                    quantity = ingredObject.getInt(INGREDIENT_QUANTITY);
                    measure = ingredObject.getString(INGREDIENT_MEASURE);
                    item = ingredObject.getString(INGREDIENT_ITEM);
                    //List Of Ingredients for each pastry
                    ingredientsList.add(new Ingredients(quantity, measure, item));
                    pastryObject.setIngredientsList(ingredientsList);
                }
                //Pastry Object Steps
                JSONArray steps = pastriesObject.getJSONArray(PASTRY_STEPS);
                List<Steps> stepsList = new ArrayList<>();
                for (int stepsToFollow = 0; stepsToFollow < steps.length(); stepsToFollow++) {
                    JSONObject stepsObject = steps.getJSONObject(stepsToFollow);
                    int id;
                    String shortDescription, description, videoUrl, thumbnailUrl;
                    id = stepsObject.getInt(STEPS_ID);
                    shortDescription = stepsObject.getString(STEPS_SHORT_DESCRIPTION);
                    description = stepsObject.getString(STEPS_DESCRIPTION);
                    videoUrl = stepsObject.getString(STEPS_VIDEO_URL);
                    thumbnailUrl = stepsObject.getString(STEPS_THUMBNAIL_URL);
                    //List of each step for each pastry
                    stepsList.add(new Steps(id, shortDescription, description, videoUrl, thumbnailUrl));
                    pastryObject.setStepsList(stepsList);
                }
                pastryObject.setId(pastryId);
                pastryObject.setName(pastryName);
                pastryObject.setServings(pastryServings);
                pastryObject.setImage(pastryImage);
                pastriesList.add(i,pastryObject);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setPastriesList(pastriesList);
        return pastriesList;
    }

    //    Pastry List setter and getter
    private static void setPastriesList(List<Pastry> pastriesList) {
        pastries = pastriesList;
    }

    public static List<Pastry> getPastries() {
        return pastries;
    }
}
