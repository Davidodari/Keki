package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class DetailActivity extends AppCompatActivity implements PastryDetailsContract.View {

    @BindView(R.id.rv_ingredients)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.btn_view_steps)
    Button btnViewSteps;
    PastryDetailsPresenter pastryDetailsPresenter;
    GridLayoutManager gridLayoutManager;
    IngredientsAdapter ingredientsAdapter;

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
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), PastryConstants.calculateNoOfColumns(this));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(gridLayoutManager);
        ingredientsRecyclerView.addItemDecoration(itemDecoration);
        ingredientsAdapter = new IngredientsAdapter(pastry.getIngredientsList());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        final List<Steps> mPastry = pastry.getStepsList();
        btnViewSteps.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pastryDetailsPresenter.onClicked(mPastry);
                    }
                }
        );
    }

    @Override
    public void openStepView(List<Steps> stepsList) {
        Intent intent = new Intent(DetailActivity.this, StepsActivity.class);
        intent.putExtra("STEPS_LIST", Parcels.wrap(stepsList));
        startActivity(intent);
    }
}
