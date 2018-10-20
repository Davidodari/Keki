package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.presenter.PastryDetailsPresenter;
import ke.co.keki.com.keki.utils.PastryConstants;

public class DetailActivity extends AppCompatActivity implements PastryDetailsContract.View {

    @BindView(R.id.rv_ingredients)
    RecyclerView ingredientsRecyclerView;
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
    }

    //4. TODO Create Fragments
    //5. TODO Hook Up Fragments in detail activity
    //6. TODO Create Phone Layout and Tab Layout
    //7. TODO Create ExoPlayer Class and Custom Layout
    //8. TODO View Testing and Data Testing in adapters
    //10. TODO Appropriate Variable Naming
    //11. TODO Animations on Open and Back
}
