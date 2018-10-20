package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.presenter.PastryDetailsPresenter;
import ke.co.keki.com.keki.utils.PastryConstants;

public class DetailActivity extends AppCompatActivity implements PastryDetailsContract.View {

    @BindView(R.id.rv_ingredients)
    RecyclerView recyclerViewIngredients;
    PastryDetailsPresenter pastryDetailsPresenter;
    GridLayoutManager gridLayoutManager;
    IngredientsAdapter ingredientsAdapter;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar mToolbar = findViewById(R.id.tb_detail_support);
        setSupportActionBar(mToolbar);
        Log.d(TAG, "supportActionBarExists::" + getSupportActionBar());
        Intent intent = getIntent();
        pastryDetailsPresenter = new PastryDetailsPresenter(this);
        if (intent != null) {
            pastryDetailsPresenter.onStart(intent);
        }
    }

    @Override
    public void bindViews(@NonNull Pastry pastry) {
        Log.d(TAG, "supportActionBarExists::" + getSupportActionBar());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(pastry.getName());
            String noOfServings = Integer.toString(pastry.getServings());
            String serves = getString(R.string.label_servings);
            String concatServe = String.format("%s : %s", serves, noOfServings);
            getSupportActionBar().setSubtitle(concatServe);
        }
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), PastryConstants.calculateNoOfColumns(this));
        recyclerViewIngredients.setHasFixedSize(true);
        recyclerViewIngredients.setLayoutManager(gridLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(pastry.getIngredientsList());
        recyclerViewIngredients.setAdapter(ingredientsAdapter);
    }


    //4. TODO Create Fragments
    //5. TODO Hook Up Fragments in detail activity
    //6. TODO Create Phone Layout and Tab Layout
    //7. TODO Create ExoPlayer Class and Custom Layout
    //8. TODO View Testing and Data Testing in adapters
    //9. TODO Add Comments and remove hard coded strings
    //10. TODO Appropriate Variable Naming
    //11. TODO Animations on Open and Back

    //Share Video or recipe
    //share app
    //settings??x
}
