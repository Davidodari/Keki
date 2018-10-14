package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.PastryDetailsContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.presenter.PastryDetailsPresenter;

public class DetailActivity extends AppCompatActivity implements PastryDetailsContract.View {

    @BindView(R.id.tv_pastry_name)
    TextView textViewPastryName;
    PastryDetailsPresenter pastryDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pastryDetailsPresenter = new PastryDetailsPresenter(this);
        pastryDetailsPresenter.onStart(intent);
    }

    @Override
    public void bindViews(Pastry pastry) {
        textViewPastryName.setText(pastry.getName());
    }

    //4. TODO Create Fragments
    //5. TODO Hook Up Fragments in detail activity
    //6. TODO Create Phone Layout and Tab Layout
    //7. TODO Create ExoPlayer Class and Custom Layout
    //8. TODO View Testing and Data Testing in adapters
    //9. TODO Add Comments and remove hard coded strings
    //10. TODO Appropriate Variable Naming
    //11. TODO Animations on Open and Back
    //Back Navigation
    //Share Video or recipe
    //share app
    //settings??x
}
