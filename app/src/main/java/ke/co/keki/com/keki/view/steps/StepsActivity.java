package ke.co.keki.com.keki.view.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.PastryConstants;
import ke.co.keki.com.keki.view.VideoStepsActivity;


public class StepsActivity extends AppCompatActivity implements StepsFragment.OnStepClickedListener {

    int stepIndex = 0;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        if (findViewById(R.id.video_linear_layout) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onStepSelected(Steps steps, int stepId) {
        //Assign The Step Id to steps Index Variable
        stepIndex = stepId;
        //Save The Index Position and Step In an Intent Extra
        final Intent intent = new Intent(this, VideoStepsActivity.class);
        intent.putExtra(PastryConstants.STEPS_ID, stepIndex);
        intent.putExtra(PastryConstants.STEPS_OBJECT, Parcels.wrap(steps));
        if (!mTwoPane) {
            //Start Activity if its phone layout
            startActivity(intent);
        }
    }
}


