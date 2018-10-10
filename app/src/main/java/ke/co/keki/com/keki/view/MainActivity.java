package ke.co.keki.com.keki.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.presenter.MainViewPastryPresenter;

public class MainActivity extends AppCompatActivity implements MainViewPastryContract.View {
    private static String TAG = MainActivity.class.getSimpleName();
    MainViewPastryPresenter pastryPresenter;
    @BindView(R.id.rv_pastries)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_load)
    ProgressBar progressBar;
    PastryAdapter mPastryAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Pastry> pastries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.tb_support_toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView.setHasFixedSize(true);
        pastryPresenter = new MainViewPastryPresenter(this);
        pastryPresenter.onStart();
        layoutManager = new LinearLayoutManager(this);
        //2.TODO Create Tab Layout and convert to grid layout
        //3.TODO Add Animations

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pastryPresenter.onDestroy();
    }


    @Override
    public void onDataLoaded(List<Pastry> pastries) {
        Log.d(TAG, "" + pastries.size());
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(layoutManager);
        mPastryAdapter = new PastryAdapter(pastries);
        mRecyclerView.setAdapter(mPastryAdapter);
    }

    @Override
    public void progressBarShow() {
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onError() {

    }
}
