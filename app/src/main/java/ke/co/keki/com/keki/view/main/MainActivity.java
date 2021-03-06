package ke.co.keki.com.keki.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ke.co.keki.com.keki.utils.JsonUtils;
import ke.co.keki.com.keki.utils.PastryConstants;
import ke.co.keki.com.keki.view.detail.DetailActivity;

/**
 * MainView Activity Class
 *
 * @author David Odari
 */
@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity implements PastryContract.View, PastryAdapter.IPastryClickHandler {

    private PastryPresenter pastryPresenter;
    @BindView(R.id.rv_pastries)
    RecyclerView mPastryListRecyclerView;
    @BindView(R.id.pb_load)
    ProgressBar progressBar;
    @BindView(R.id.tv_error)
    TextView textViewError;
    @BindView(R.id.iv_offline)
    ImageView imageViewNetworkConnection;
    PastryAdapter mPastryAdapter;
    @BindView(R.id.progress_layout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pastryPresenter = new PastryPresenter(this);
        if (pastryPresenter.checkNetworkConnection(this)) {
            pastryPresenter.onStart();
        } else {
            pastryPresenter.onError();
        }
    }

    /**
     * On Activity Deatroyed call off async task
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pastryPresenter.onDestroy();
    }

    /**
     * @param pastries list of pastries to be passed on to adapter after being converted from
     *                 JSON to Java Objects
     */
    @Override
    public void onDataLoaded(List<Pastry> pastries) {
        setupRecyclerView(pastries);
    }

    /**
     * While Model Loads Show Progess Bar
     */
    @Override
    public void progressBarShow() {
        Log.d("Display Progress Bar", "shouldBeVisible");
        progressBar.setVisibility(View.VISIBLE);
        mPastryListRecyclerView.setVisibility(View.GONE);
    }

    /**
     * On network connection Error Display message
     */
    @Override
    public void onError() {
        textViewError.setVisibility(View.VISIBLE);
        imageViewNetworkConnection.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        mPastryListRecyclerView.setVisibility(View.GONE);
    }


    private void setupRecyclerView(List<Pastry> pastries) {
        if (textViewError.getVisibility() == View.VISIBLE) {
            textViewError.setVisibility(View.GONE);
            imageViewNetworkConnection.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        mPastryListRecyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), PastryConstants.calculateNoOfColumns(this));
        mPastryListRecyclerView.setHasFixedSize(true);
        mPastryListRecyclerView.setLayoutManager(gridLayoutManager);
        mPastryListRecyclerView.setItemViewCacheSize(4);
        mPastryListRecyclerView.setDrawingCacheEnabled(true);
        mPastryAdapter = new PastryAdapter(pastries, this);
        mPastryListRecyclerView.setAdapter(mPastryAdapter);
    }

    /**
     * Opens Detaill Activity
     *
     * @param pastry pastry item clicked and passed to details activity
     */
    @Override
    public void onPastryCardClicked(Pastry pastry) {
        Intent openDetailActivity = new Intent(MainActivity.this, DetailActivity.class);
        openDetailActivity.putExtra(PastryConstants.PASTRY, Parcels.wrap(pastry));
        startActivity(openDetailActivity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PastryConstants.PASTRY_LIST, Parcels.wrap(JsonUtils.getPastries()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(PastryConstants.PASTRY_LIST)) {
            List<Pastry> pastries = Parcels.unwrap(savedInstanceState.getParcelable(PastryConstants.PASTRY_LIST));
            if (pastries != null && pastries.size() > 0) {
                setupRecyclerView(pastries);
            }
        }
    }
}
