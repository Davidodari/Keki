package ke.co.keki.com.keki.presenter;

import java.util.List;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.MainViewPastryModel;
import ke.co.keki.com.keki.model.pojo.Pastry;

public class MainViewPastryPresenter implements MainViewPastryContract.Presenter {

    private static final String TAG = MainViewPastryPresenter.class.getSimpleName();

    private MainViewPastryContract.View view;
    private MainViewPastryContract.Model model;


    public MainViewPastryPresenter(MainViewPastryContract.View view) {
        this.view = view;
        this.model = new MainViewPastryModel();
    }


    @Override
    public void onDestroy() {
        model.endNetworkCall();
    }

    @Override
    public void onError() {
        view.onError();
    }

    @Override
    public void stateLoading() {
        view.progressBarShow();
    }

    @Override
    public void getPastryList(List<Pastry> pastries) {
        view.onDataLoaded(pastries);
    }

    @Override
    public void onStart() {
        model.startNetworkCall(this);
    }


}
