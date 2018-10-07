package ke.co.keki.com.keki.utils;

import org.json.JSONException;
import org.json.JSONObject;

import ke.co.keki.com.keki.model.pojo.Pastry;

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
    private final static String STEPS_SHORT_DESCIPTION = "shortdescription";
    private final static String STEPS_DESCRIPTION = "description";
    private final static String STEPS_VIDEO_URL = "videoURL";
    private final static String STEPS_THUMBNAIL_URL = "thumbnailURL";

    private static Pastry pastryObject;


    public static Pastry parseJSON(String jsonObject) {
        try {
            JSONObject pastryJson = new JSONObject(jsonObject);
            //Pastry Id
            int pastryId = pastryJson.getInt(PASTRY_ID);
            String pastryName = pastryJson.getString(PASTRY_NAME);
            int pastryServings = pastryJson.getInt(PASTRY_SERVINGS);
            String pastryImage = pastryJson.getString(PASTRY_IMAGE);
            pastryObject = new Pastry(pastryId, pastryName, pastryServings, pastryImage);

        } catch (JSONException e) {
        }

        return pastryObject;
    }
}
