package ke.co.keki.com.keki.model;


import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ke.co.keki.com.keki.contract.PastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.utils.JsonUtils;
import ke.co.keki.com.keki.utils.NetworkUtils;


public class PastryModel implements PastryContract.Model {

    private static final String JSON_PATH_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private LoadPastriesTask loadPastriesTask;

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

        LoadPastriesTask(PastryContract.Presenter presenter) {
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
            if (strings != null) {
                List<Pastry> pastryList = JsonUtils.parseJSON(strings);
                if (pastryList != null) {
                    presenter.getPastryList(pastryList);
                } else {
                    presenter.onError();
                }
            } else {
                presenter.onError();
            }
        }
    }

}