package ke.co.keki.com.keki.view.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Ingredients;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.model.room_database.PastryDatabase;
import ke.co.keki.com.keki.presenter.PastryDetailsPresenter;
import ke.co.keki.com.keki.utils.PastryConstants;
import ke.co.keki.com.keki.view.steps.StepsActivity;

/**
 * Activity That Displays Ingredients List
 */
public class DetailActivity extends AppCompatActivity implements PastryDetailsContract.View {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_ingredients)
    RecyclerView ingredientsRecyclerView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.btn_view_steps)
    Button btnViewSteps;
    @BindView(R.id.iv_fav_con)
    ImageView favConimageView;
    @BindView(R.id.sw_option_display_on_widget)
    Switch swDisplaySelectedIngredients;
    private PastryDetailsPresenter pastryDetailsPresenter;
    public static List<Steps> steps;
    public Pastry pastry;
    private PastryDatabase pastryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        pastryDatabase = PastryDatabase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        pastryDetailsPresenter = new PastryDetailsPresenter(this, pastryDatabase);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PastryConstants.PASTRY)) {
                Pastry pastry = Parcels.unwrap(savedInstanceState.getParcelable(PastryConstants.PASTRY));
                if (pastry != null) {
                    Log.d("Pastry :", pastry.getName());
                    bindViews(pastry);
                }
            }
        } else if (intent != null) {
            pastryDetailsPresenter.onStart(intent);
        }
    }

    /**
     * ;     * @param pastry pastry object clicked
     */
    @Override
    public void bindViews(@NonNull final Pastry pastry) {
        //SetupAction Bar to display pastry title and serving quantity
        setUpActionBar(pastry);

        //Setup Recycler View To Display Ingredients List
        setupRecyclerView(pastry.getIngredientsList());

        //On CLicked Opens Steps Activity and passes in list of steps
        final List<Steps> mPastry = pastry.getStepsList();
        btnViewSteps.setOnClickListener(
                v -> pastryDetailsPresenter.onClicked(mPastry)
        );
        this.pastry = pastry;
        //Check Preference and set state of switch button
        checkSharedPreferenceAndSetState(pastry);
        swDisplaySelectedIngredients.setOnClickListener(v -> {
            // check current state of  Switch (true or false).
            Boolean switchState = swDisplaySelectedIngredients.isChecked();
            if (switchState) {
                Toast.makeText(DetailActivity.this, "Updated Widget", Toast.LENGTH_SHORT).show();
                pastryDetailsPresenter.addPreferenceAndUpdateWidget(this, pastry);
            } else {
                pastryDetailsPresenter.removePreferenceAndUpdateWidget(this);
                Toast.makeText(DetailActivity.this, "Removed From Widget", Toast.LENGTH_SHORT).show();
            }
        });

        //Room Database Operations
        //Check if its in database and display Drawable showing it exists
        pastryDetailsPresenter.checkFromDatabase(pastry);
        //Add or Remove From Database
        favConimageView.setOnClickListener(v -> pastryDetailsPresenter.databaseOperations(this, pastry));
    }

    /**
     * @param stepsList steps list to be opened in new activity with fragments
     */
    @Override
    public void openStepView(List<Steps> stepsList) {
        //intent to steps activity
        Intent intent = new Intent(DetailActivity.this, StepsActivity.class);
        //put steps list as extra to steps activity
        intent.putExtra(PastryConstants.STEPS_LIST, Parcels.wrap(stepsList));
        //Fragment Arguments
        steps = stepsList;
        //start Steps Activity
        startActivity(intent);
    }

    //Change Icon When Added or Removed From Database
    @Override
    public void onAddedToDatabase() {
        runOnUiThread(() -> favConimageView.setImageDrawable(getDrawable(R.drawable.ic_favorite_fill)));

    }

    @Override
    public void onRemovedFromDatabase() {
        runOnUiThread(() -> favConimageView.setImageDrawable(getDrawable(R.drawable.ic_favorite_empty)));
    }

    /**
     * @param pastry check whether data value in shared preferences are equal to the current pastry being displayed
     *               and show swicth button state.
     */
    private void checkSharedPreferenceAndSetState(@NonNull Pastry pastry) {
        SharedPreferences preferences = getSharedPreferences(PastryConstants.SHARED_PREFERENCE_KEY, 0);
        String pastryName = preferences.getString(PastryConstants.PREFERENCE_RECIPE_NAME_KEY, getString(R.string.app_name));
        if (pastryName != null && !pastryName.equals("") && pastryName.equals(pastry.getName())) {
            swDisplaySelectedIngredients.setChecked(true);
        } else {
            swDisplaySelectedIngredients.setChecked(false);
        }
    }

    /**
     * @param ingredients set Recycler View Adapter with a list of ingredients to display
     */
    private void setupRecyclerView(List<Ingredients> ingredients) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), PastryConstants.calculateNoOfColumns(this));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(gridLayoutManager);
        ingredientsRecyclerView.addItemDecoration(itemDecoration);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }

    private void setUpActionBar(Pastry pastry) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pastry.getName());
            String noOfServings = Integer.toString(pastry.getServings());
            String serves = getString(R.string.label_servings);
            String concatServe = String.format("%s : %s", serves, noOfServings);
            getSupportActionBar().setSubtitle(concatServe);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(PastryConstants.PASTRY, Parcels.wrap(pastry));
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
