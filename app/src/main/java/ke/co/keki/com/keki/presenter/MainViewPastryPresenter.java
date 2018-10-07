package ke.co.keki.com.keki.presenter;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.utils.JsonUtils;
import ke.co.keki.com.keki.utils.NetworkUtils;

public class MainViewPastryPresenter implements MainViewPastryContract.Presenter {

    private static final String TAG = MainViewPastryPresenter.class.getSimpleName();
    private static final String JSON_PATH_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private MainViewPastryContract.View view;
    private MainViewPastryContract.Model model;
    LoadPastriesTask loadPastriesTask;

    public MainViewPastryPresenter(MainViewPastryContract.View view, MainViewPastryContract.Model model) {
        this.view = view;
        this.model = model;
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

    //Recycler View Presenter implementations
    @Override
    public void onBindPastryViewAtPosition(int position, MainViewPastryContract.View.RecyclerViewData recyclerViewSetup) {
        Pastry pastry = model.getPastryData().get(position);
        recyclerViewSetup.setPastryImage(pastry.getImage());
        recyclerViewSetup.setPastryName(pastry.getName());
        recyclerViewSetup.setPastryServing(pastry.getServings());
        Log.d(TAG, "onBindPastryViewAtPosition: " + pastry.getName());
    }

    @Override
    public int getPastryItemCount() {
        return model.getPastryData().size();
    }

    //AsyncTask
    public static class LoadPastriesTask extends AsyncTask<URL, Void, String> {

        private final MainViewPastryContract.Presenter presenter;
        private final MainViewPastryContract.View view;

        public LoadPastriesTask(MainViewPastryContract.Presenter presenter, MainViewPastryContract.View view) {
            this.presenter = presenter;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                view.displayJson(strings);
                //JsonUtils.parseJSON(strings);
            }
        }
    }
}
