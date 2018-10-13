package ke.co.keki.com.keki.presenter;

import android.content.Intent;

import org.parceler.Parcels;

import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.PastryDetailsModel;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.utils.PastryConstants;

public class PastryDetailsPresenter implements PastryDetailsContract.Presenter {

    private PastryDetailsContract.Model model;
    private PastryDetailsContract.View view;


    public PastryDetailsPresenter(PastryDetailsContract.View view) {
        this.view = view;
        this.model = new PastryDetailsModel();
    }

    @Override
    public void onStart(Intent intent) {
        if (intent.hasExtra(PastryConstants.PASTRY)) {
            Pastry pastry = Parcels.unwrap(intent.getParcelableExtra(PastryConstants.PASTRY));
            view.bindViews(pastry);
        }
    }
}
