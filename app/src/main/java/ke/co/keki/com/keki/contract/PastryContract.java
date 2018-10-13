package ke.co.keki.com.keki.contract;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface PastryContract {
    interface Model {

        boolean requestNetworkPermission(Activity activity);

        void startNetworkCall(Presenter presenter);

        void endNetworkCall();

    }

    interface View {
        void onDataLoaded(List<Pastry> pastries);

        void progressBarShow();

        void onError();

    }

    interface Presenter {
        boolean checkNetworkConnection(Context context);

        boolean checkPermissions(Activity activity);

        void asyncLoadingInBackground();

        void getPastryList(List<Pastry> pastries);

        void onStart();

        void onDestroy();

        void onError();

    }
}
