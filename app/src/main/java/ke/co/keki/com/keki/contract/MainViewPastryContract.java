package ke.co.keki.com.keki.contract;

import android.content.Context;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface MainViewPastryContract {
    interface Model {

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

        void asyncLoadingInBackground();

        void getPastryList(List<Pastry> pastries);

        void onStart();

        void onDestroy();

        void onError();

    }
}
