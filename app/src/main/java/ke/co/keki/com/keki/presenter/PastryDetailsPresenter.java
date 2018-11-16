package ke.co.keki.com.keki.presenter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.model.room_database.AppExecutors;
import ke.co.keki.com.keki.model.room_database.PastryDatabase;
import ke.co.keki.com.keki.utils.IngredientSharedPreference;
import ke.co.keki.com.keki.widget.IngredientsWidget;

public class PastryDetailsPresenter implements PastryDetailsContract.Presenter {

    private PastryDetailsContract.View view;
    private PastryDatabase pastryDatabase;

    public PastryDetailsPresenter(PastryDetailsContract.View view, PastryDatabase pastryDatabase) {
        this.view = view;
        this.pastryDatabase = pastryDatabase;
    }

    //On Start Of Details Activity bind views
    @Override
    public void onStart(Pastry pastry) {
            if (pastry != null) {
                view.bindViews(pastry);
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
                //Set Drawable
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

    @Override
    public void addPreferenceAndUpdateWidget(Context context, Pastry pastry) {
        //Add To Shared Preference
        IngredientSharedPreference.setIngredientListPreference(context, pastry.getIngredientsList());
        IngredientSharedPreference.setRecipeNamePreference(pastry.getName(), context);
        updateWidget(context);

    }

    //Removes Preference and Updated Widget to default Values
    @Override
    public void removePreferenceAndUpdateWidget(Context ctx) {
        IngredientSharedPreference.removePreferences(ctx);
        updateWidget(ctx);
    }

    private void updateWidget(Context context) {
        //Update Widget Immediately
        Intent intent = new Intent(context, IngredientsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context.getApplicationContext())
                .getAppWidgetIds(new ComponentName(context.getApplicationContext(), IngredientsWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }
}