package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.utils.ConstantsPastry;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_pastry_name)
    TextView textViewPastryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //4. TODO Create Fragments
        //5. TODO Hook Up Fragments in detail activity
        //6. TODO Create Phone Layout and Tab Layout
        //7. TODO Create ExoPlayer Class and Custom Layout
        //8. TODO View Testing and Data Testing in adapters
        //9. TODO Add Comments and remove hard coded strings
        //10. TODO Appropriate Variable Naming
        //11. TODO Animations on Open and Back
        Intent intent = getIntent();
        if (intent.hasExtra(ConstantsPastry.PASTRY_NAME)) {
            String pastryName = Parcels.unwrap(intent.getParcelableExtra(ConstantsPastry.PASTRY_NAME));
            textViewPastryName.setText(pastryName);
        }
    }

    //Back Navigation
    //Share Video or recipe
    //share app
    //settings??x
}
