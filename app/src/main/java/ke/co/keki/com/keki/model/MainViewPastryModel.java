package ke.co.keki.com.keki.model;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.utils.JsonUtils;
import ke.co.keki.com.keki.utils.NetworkUtils;


public class MainViewPastryModel implements MainViewPastryContract.Model {

    private static final String TAG = MainViewPastryModel.class.getSimpleName();
    private static final String JSON_PATH_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    LoadPastriesTask loadPastriesTask;
    public static List<Pastry> pastryList;


    /**
     * @param presenter presenter to which changes will be updated and notified
     *                  Starts network call
     */
    @Override
    public void startNetworkCall(MainViewPastryContract.Presenter presenter) {
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

        private MainViewPastryContract.Presenter presenter;

        public LoadPastriesTask(MainViewPastryContract.Presenter presenter) {
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
            if (strings != null & !strings.equals("")) {
                //json response
                pastryList = JsonUtils.parseJSON(strings);
                Log.d(TAG, "onDataLoadedItems:" + pastryList.size());
                if (pastryList != null) {
                    presenter.getPastryList(pastryList);
                } else {
                    presenter.onError();
                }
            }
        }
    }
}