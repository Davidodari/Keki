package ke.co.keki.com.keki.contract;

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

        void stateLoading();

        void getPastryList(List<Pastry> pastries);

        //called on onCreate of view
        void onStart();

        //called on onDestroy of view
        void onDestroy();

        void onError();

    }
}
