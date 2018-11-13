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

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;
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
    private PastryDetailsPresenter pastryDetailsPresenter;
    public static List<Steps> steps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar mToolbar = findViewById(R.id.tb_detail_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        pastryDetailsPresenter = new PastryDetailsPresenter(this);
        if (intent != null) {
            pastryDetailsPresenter.onStart(intent);
        }
    }

    /**
     * ;     * @param pastry pastry object clicked
     */
    @Override
    public void bindViews(@NonNull Pastry pastry) {
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

}
