package ke.co.keki.com.keki.presenter;

import android.util.Log;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;

public class MainViewPastryPresenter implements MainViewPastryContract.Presenter {

    private String TAG = MainViewPastryPresenter.class.getSimpleName();
    private MainViewPastryContract.View view;
    private MainViewPastryContract.Model model;

    public MainViewPastryPresenter(MainViewPastryContract.View view, MainViewPastryContract.Model model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void onCardSelected(Pastry pastry) {
        view.displayAnimations();
        view.openIntent();
    }

    @Override
    public void onDestroy() {

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
}
