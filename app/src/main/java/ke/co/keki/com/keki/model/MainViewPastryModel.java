package ke.co.keki.com.keki.model;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.room_database.PastryDatabase;
import ke.co.keki.com.keki.presenter.MainViewPastryPresenter;


public class MainViewPastryModel extends AndroidViewModel implements MainViewPastryContract.Model {
    private static final String TAG = MainViewPastryModel.class.getSimpleName();
    PastryDatabase db;
    List<Pastry> pastryList;
    MainViewPastryPresenter presenter;

    public MainViewPastryModel(@NonNull Application application) {
        super(application);
        db = PastryDatabase.getInstance(this.getApplication());

    }
}