package ke.co.keki.com.keki.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.PastryDetailsContract;
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
    private PastryDetailsPresenter pastryDetailsPresenter;
    public static List<Steps> steps;
    private PastryDatabase pastryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        pastryDatabase = PastryDatabase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        pastryDetailsPresenter = new PastryDetailsPresenter(this, pastryDatabase);
        if (intent != null) {
            pastryDetailsPresenter.onStart(intent);
        }
    }

    /**
     * ;     * @param pastry pastry object clicked
     */
    @Override
    public void bindViews(@NonNull final Pastry pastry) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pastry.getName());
            String noOfServings = Integer.toString(pastry.getServings());
            String serves = getString(R.string.label_servings);
            String concatServe = String.format("%s : %s", serves, noOfServings);
            getSupportActionBar().setSubtitle(concatServe);
        }
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), PastryConstants.calculateNoOfColumns(this));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(gridLayoutManager);
        ingredientsRecyclerView.addItemDecoration(itemDecoration);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(pastry.getIngredientsList());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        final List<Steps> mPastry = pastry.getStepsList();
        btnViewSteps.setOnClickListener(
                v -> pastryDetailsPresenter.onClicked(mPastry)
        );
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

}
