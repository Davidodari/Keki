package ke.co.keki.com.keki.model;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ke.co.keki.com.keki.contract.PastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.utils.JsonUtils;
import ke.co.keki.com.keki.utils.NetworkUtils;


public class PastryModel implements PastryContract.Model {

    private static final String TAG = PastryModel.class.getSimpleName();
    private static final String JSON_PATH_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    LoadPastriesTask loadPastriesTask;
    public static List<Pastry> pastryList;
    public boolean isPermissionGranted;


    @Override
    public boolean requestNetworkPermission(final Activity activity) {
        Dexter.withActivity(activity)
                .withPermission(
                        Manifest.permission.ACCESS_NETWORK_STATE
                )
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermissionGranted = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        isPermissionGranted = false;
                        // check for permanent denial of any permission
                        if (response.isPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle("Need Permissions");
                            builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
                            builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                    intent.setData(uri);
                                    activity.startActivityForResult(intent, 101);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Log.e("Dexter", "There was an error: " + error.toString());
                    }
                })
                .onSameThread()
                .check();

        return isPermissionGranted;
    }


    /**
     * @param presenter presenter to which changes will be updated and notified
     *                  Starts network call
     */
    @Override
    public void startNetworkCall(PastryContract.Presenter presenter) {
        if (loadPastriesTask == null || loadPastriesTask.getStatus() != AsyncTask.Status.RUNNING) {
            loadPastriesTask = new LoadPastriesTask(presenter);
            URL networkCall = NetworkUtils.builtUrl(JSON_PATH_URL);
            loadPastriesTask.execute(networkCall);
        }
    }

    /**
     * ends network call when user destroys app activivty
     */
    @Override
    public void endNetworkCall() {
        //halts async task process on destroyed
        if (loadPastriesTask != null && loadPastriesTask.getStatus() == AsyncTask.Status.RUNNING) {
            loadPastriesTask.cancel(true);
        }
    }


    //AsyncTask
    public static class LoadPastriesTask extends AsyncTask<URL, Void, String> {

        private PastryContract.Presenter presenter;

        public LoadPastriesTask(PastryContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            presenter.asyncLoadingInBackground();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL Url = urls[0];
            Log.d(TAG, "searchUrl" + urls[0]);
            String urlJsonResponse = null;
            try {
                urlJsonResponse = NetworkUtils.getResponseFromHttpUrl(Url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return urlJsonResponse;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            //JSON Response
            Log.d(TAG, "jsonResponse:" + strings);
            if (strings != null) {
                //json response
                pastryList = JsonUtils.parseJSON(strings);
                Log.d(TAG, "onDataLoadedItems:" + pastryList.size());
                if (pastryList != null) {
                    presenter.getPastryList(pastryList);
                } else {
                    presenter.onError();
                }
            }else {
                presenter.onError();
            }
        }
    }

}