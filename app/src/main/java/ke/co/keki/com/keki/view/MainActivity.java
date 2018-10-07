package ke.co.keki.com.keki.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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
    @BindView(R.id.tv_mock_test)
    TextView mJsonTextView;
    PastryAdapter mPastryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.tb_support_toolbar);
        setSupportActionBar(toolbar);
        pastryPresenter = new MainViewPastryPresenter(this, new MainViewPastryModel());
        pastryPresenter.onStart();
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
    public void displayOnLoadError() {

    }

    @Override
    public void displayAnimations() {

    }

    @Override
    public void displayLoadAnimations() {

    }

    @Override
    public void displayJson(String string) {
        mJsonTextView.setText(string);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pastryPresenter.onDestroy();
    }
}
