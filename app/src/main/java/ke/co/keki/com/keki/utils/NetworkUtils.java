package ke.co.keki.com.keki.utils;

import android.net.Uri;
import android.webkit.URLUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private static final String JSON_PATH_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL builtUrl() {
        Uri builtJsonUri = Uri.parse(JSON_PATH_URL);
        URL url = null;
        try {
            url = new URL(builtJsonUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return (URLUtil.isValidUrl(url.toString())) ? url : null;
    }
}
