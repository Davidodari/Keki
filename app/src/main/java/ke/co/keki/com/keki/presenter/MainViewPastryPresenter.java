package ke.co.keki.com.keki.presenter;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.utils.JsonUtils;
import ke.co.keki.com.keki.utils.NetworkUtils;

public class MainViewPastryPresenter implements MainViewPastryContract.Presenter {

    private static final String TAG = MainViewPastryPresenter.class.getSimpleName();
    private static final String JSON_PATH_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private MainViewPastryContract.View view;
    LoadPastriesTask loadPastriesTask;
    public static List<Pastry> mPastriesList;


    public MainViewPastryPresenter(MainViewPastryContract.View view) {
        this.view = view;
    }


    @Override
    public void onDestroy() {
        //halts async task process on destroyed
        if (loadPastriesTask != null && loadPastriesTask.getStatus() == AsyncTask.Status.RUNNING) {
            loadPastriesTask.cancel(true);
        }
    }

    @Override
    public void onStart() {

        if (loadPastriesTask == null || loadPastriesTask.getStatus() != AsyncTask.Status.RUNNING) {
            loadPastriesTask = new LoadPastriesTask(this, view);
            URL networkCall = NetworkUtils.builtUrl(JSON_PATH_URL);
            loadPastriesTask.execute(networkCall);
        }
    }

    //AsyncTask
    public static class LoadPastriesTask extends AsyncTask<URL, Void, String> {

        private final MainViewPastryContract.View view;
        private MainViewPastryContract.Presenter presenter;

        public LoadPastriesTask(MainViewPastryContract.Presenter presenter, MainViewPastryContract.View view) {
            this.view = view;
            this.presenter = presenter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.progressBarShow();
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
                mPastriesList = JsonUtils.parseJSON(strings);
                Log.d(TAG, "onDataLoadedItems:" + mPastriesList.size());
                view.onDataLoaded(mPastriesList);
            }
        }
    }
}
