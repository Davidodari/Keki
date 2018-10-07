package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.MainViewPastryModel;
import ke.co.keki.com.keki.presenter.MainViewPastryPresenter;

public class MainActivity extends AppCompatActivity implements MainViewPastryContract.View {
    MainViewPastryPresenter pastryPresenter;
    @BindView(R.id.rv_pastries)
    RecyclerView mRecyclerView;
    PastryAdapter mPastryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pastryPresenter = new MainViewPastryPresenter(this, new MainViewPastryModel());
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mPastryAdapter = new PastryAdapter(pastryPresenter);
        mRecyclerView.setAdapter(mPastryAdapter);
    }


    @Override
    public void displayError() {

    }

    @Override
    public void displayAnimations() {

    }

    @Override
    public void diplayLoadAnimations() {

    }

    @Override
    public void openIntent() {
        Intent openRecipeDetails = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(openRecipeDetails);
    }

}
