package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.PastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.presenter.PastryPresenter;
import ke.co.keki.com.keki.utils.PastryConstants;

/**
 * MainView Activity Class
 *
 * @author David Odari
 */
public class MainActivity extends AppCompatActivity implements PastryContract.View, PastryAdapter.IPastryClickHandler {

    private static String TAG = MainActivity.class.getSimpleName();
    PastryPresenter pastryPresenter;
    @BindView(R.id.rv_pastries)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_load)
    ProgressBar progressBar;
    @BindView(R.id.tv_error)
    TextView textViewError;
    @BindView(R.id.iv_offline)
    ImageView imageViewNetworkConnection;
    PastryAdapter mPastryAdapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.tb_support_toolbar);
        setSupportActionBar(toolbar);
        pastryPresenter = new PastryPresenter(this);
        if (pastryPresenter.checkPermissions(this)) {
            if (pastryPresenter.checkNetworkConnection(this)) {
                pastryPresenter.onStart();
            } else {
                pastryPresenter.onError();
            }
        } else {

        }
    }

    //TODO GridView
    //TODO Firebase

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
        if (textViewError.getVisibility() == View.VISIBLE) {
            textViewError.setVisibility(View.GONE);
            imageViewNetworkConnection.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        setupRecyclerView(pastries);
    }

    @Override
    public void progressBarShow() {
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        textViewError.setVisibility(View.VISIBLE);
        imageViewNetworkConnection.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }

    public void setupRecyclerView(List<Pastry> pastries) {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mPastryAdapter = new PastryAdapter(pastries, this);
        mRecyclerView.setAdapter(mPastryAdapter);
    }

    @Override
    public void onPastryCardClicked(Pastry pastry, View v) {
        Intent openDetailActivity = new Intent(MainActivity.this, DetailActivity.class);
        openDetailActivity.putExtra(PastryConstants.PASTRY, Parcels.wrap(pastry));
        startActivity(openDetailActivity);
    }
}
