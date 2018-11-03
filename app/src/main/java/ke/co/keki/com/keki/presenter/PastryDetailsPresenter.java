package ke.co.keki.com.keki.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import org.parceler.Parcels;

import java.util.List;

import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.PastryConstants;

public class PastryDetailsPresenter implements PastryDetailsContract.Presenter {


    private PastryDetailsContract.View view;


    public PastryDetailsPresenter(PastryDetailsContract.View view) {
        this.view = view;
    }

    //On Start Of Details Activity bind views
    @Override
    public void onStart(@NonNull Intent intent) {
        if (intent.hasExtra(PastryConstants.PASTRY)) {
            Pastry pastry = Parcels.unwrap(intent.getParcelableExtra(PastryConstants.PASTRY));
            if (pastry != null) {
                view.bindViews(pastry);
            }
        }
    }

    //Opens Steps Intent
    @Override
    public void onClicked(List<Steps> stepsList) {
        view.openStepView(stepsList);
    }
}
