package ke.co.keki.com.keki.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.presenter.MainViewPastryPresenter;

public class MainActivity extends AppCompatActivity implements MainViewPastryPresenter.View {

    private MainViewPastryPresenter mainViewPastryPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showRecipeCards() {

    }

    @Override
    public void showOnErrorView() {

    }

    @Override
    public void showLoadAnimation() {

    }

    @Override
    public void showExitAnimation() {

    }
}
