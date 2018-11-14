package ke.co.keki.com.keki.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.parceler.Parcels;

import java.util.List;

import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.model.room_database.AppExecutors;
import ke.co.keki.com.keki.model.room_database.PastryDatabase;
import ke.co.keki.com.keki.utils.PastryConstants;

public class PastryDetailsPresenter implements PastryDetailsContract.Presenter {


    private PastryDetailsContract.View view;

    private boolean isAddedToDatabase;
    private PastryDatabase pastryDatabase;

    public PastryDetailsPresenter(PastryDetailsContract.View view, PastryDatabase pastryDatabase) {
        this.view = view;
        this.pastryDatabase = pastryDatabase;
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

    @Override
    public void databaseOperations(final Pastry pastry) {
        Log.d("Database Operations", "Adding = ".concat(pastry.getName()));
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("Add Model Db =", "Adding" + pastry.getName().concat(pastryDatabase.toString()));
                List<Pastry> pastries = pastryDatabase.pastryDao().loadPastryById(pastry.getId());
                if (pastries.size() == 0) {
                    pastryDatabase.pastryDao().insertPastry(pastry);
                    isAddedToDatabase = true;
                    view.displayOnAddToast();
                } else {
                    pastryDatabase.pastryDao().removePastry(pastry);
                    isAddedToDatabase = false;
                    view.displayOnRemoveToast();

                }
            }
        });
    }

    @Override
    public boolean checkFromDatabase(Pastry pastry) {
        return false;
    }
}