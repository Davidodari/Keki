package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import java.util.List;

import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        Intent intent = getIntent();
        if (intent.hasExtra("STEPS_LIST")) {
            List<Steps> stepsList = Parcels.unwrap(intent.getParcelableExtra("STEPS_LIST"));
        }

    }
}
