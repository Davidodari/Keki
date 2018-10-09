package ke.co.keki.com.keki.contract;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface MainViewPastryContract {
    interface Model {
    }

    interface View {
        void onDataLoaded(List<Pastry> pastries);

        void progressBarShow();

    }

    interface Presenter {
        //called on onCreate of view
        void onStart();

        //called on onDestroy of view
        void onDestroy();

    }
}
