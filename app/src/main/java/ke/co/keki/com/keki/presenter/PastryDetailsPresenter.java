package ke.co.keki.com.keki.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.model.room_database.AppExecutors;
import ke.co.keki.com.keki.model.room_database.PastryDatabase;
import ke.co.keki.com.keki.utils.PastryConstants;
import ke.co.keki.com.keki.widget.IngredientUpdateService;

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

    /**
     * @param pastry Add or Remove pastry from database
     */
    @Override
    public void databaseOperations(Context context, final Pastry pastry) {
        Log.d("Database Operations", "Adding = ".concat(pastry.getName()));
        AppExecutors.getInstance().diskIO().execute(() -> {
            //Pastries in database
            List<Pastry> pastries = pastryDatabase.pastryDao().loadPastryById(pastry.getId());
            if (pastries.size() == 0) {
                Date dateToday = Calendar.getInstance(Locale.getDefault()).getTime();
                Log.d("Time Now", "" + dateToday);
                Pastry newPastryObject = new Pastry(pastry.getId(), pastry.getName(), pastry.getIngredientsList(), pastry.getServings(), pastry.getImage(), dateToday);
                //Add To Database and set imageview
                pastryDatabase.pastryDao().insertPastry(newPastryObject);
                IngredientUpdateService.updateIngredient(context,pastry.getId());
                view.onAddedToDatabase();
            } else {
                //Remove from Database and set imageview
                pastryDatabase.pastryDao().removePastry(pastry);
                view.onRemovedFromDatabase();
            }
        });
    }

    /**
     * @param pastry If it exists in Database display image view with favourite image drawable
     * @return
     */
    @Override
    public void checkFromDatabase(Pastry pastry) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Pastry> pastryInDb = pastryDatabase.pastryDao().loadPastryById(pastry.getId());
            if (pastryInDb.size() > 0) {
                view.onAddedToDatabase();
            }
        });
    }
}