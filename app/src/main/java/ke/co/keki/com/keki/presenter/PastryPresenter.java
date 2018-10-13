package ke.co.keki.com.keki.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import ke.co.keki.com.keki.contract.PastryContract;
import ke.co.keki.com.keki.model.PastryModel;
import ke.co.keki.com.keki.model.pojo.Pastry;

public class PastryPresenter implements PastryContract.Presenter {

    private PastryContract.View view;
    private PastryContract.Model model;


    public PastryPresenter(PastryContract.View view) {
        this.view = view;
        this.model = new PastryModel();
    }

    /**
     * Called on {@link ke.co.keki.com.keki.view.MainActivity} on destroy and terminates async task
     * if its running
     */
    @Override
    public void onDestroy() {
        model.endNetworkCall();
    }

    /**
     * Called when InternetConnection is not available or when pastry list is null
     */
    @Override
    public void onError() {
        view.onError();
    }

    /**
     * Checks Network State returns true if connected
     *
     * @param context checks network connection on main view before loading data
     * @return boolean network state if connected true else false
     */
    @Override
    public boolean checkNetworkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return false;
        }

    }

    @Override
    public boolean checkPermissions(Activity activity) {
        return model.requestNetworkPermission(activity);
    }

    /**
     * Displays Progress bar while Items Are Being Loaded from Url Connection
     */
    @Override
    public void asyncLoadingInBackground() {
        view.progressBarShow();
    }

    /**
     * @param pastries list of pastries loaded from async task response and passed to view to populate
     *                 ui
     */
    @Override
    public void getPastryList(List<Pastry> pastries) {
        view.onDataLoaded(pastries);
    }

    /**
     * on initialise presenter called first on {@link ke.co.keki.com.keki.view.MainActivity} onCreate Method
     */
    @Override
    public void onStart() {
        model.startNetworkCall(this);
    }


}
