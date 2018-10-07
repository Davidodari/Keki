package ke.co.keki.com.keki.utils;

import android.net.Uri;
import android.webkit.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {


    public static URL builtUrl(String url) {
        Uri builtJsonUri = Uri.parse(url);
        URL Url = null;
        try {
            Url = new URL(builtJsonUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return (URLUtil.isValidUrl(url)) ? Url : null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
