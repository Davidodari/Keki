package ke.co.keki.com.keki.view.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.PastryConstants;
import ke.co.keki.com.keki.view.video.VideoFragment;
import ke.co.keki.com.keki.view.video.VideoStepsActivity;


public class StepsActivity extends AppCompatActivity implements StepsFragment.OnStepClickedListener {

    private boolean mTwoPane;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        //Checks if its tab layout or not curently on display returns boolean
        mTwoPane = findViewById(R.id.video_linear_layout) != null;
        //If theres no saved state or  on fragment start display placeholder
        if (savedInstanceState == null) {
            //If its tab layout execute the following code
            if (mTwoPane) {
                VideoFragment videoFragment = new VideoFragment();
                videoFragment.setVideoUrl(null);
                videoFragment.setCurrentSteps(null);
                videoFragment.setDescription(getString(R.string.pick_a_step));
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frag_player_container, videoFragment)
                        .commit();
            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.label_steps_section));
        }
    }

    @Override
    public void onStepSelected(Steps steps, int stepId) {

        //Save The Index Position and Step In an Intent Extra
        final Intent intent = new Intent(this, VideoStepsActivity.class);
        intent.putExtra(PastryConstants.STEPS_ID, stepId);
        intent.putExtra(PastryConstants.STEPS_OBJECT, Parcels.wrap(steps));
        if (!mTwoPane) {
            //Start Activity if its phone layout
            startActivity(intent);

        } else {
            //If its tab layout
            String desc = steps.getDescription();
            String videoUrl = steps.getVideoUrl();
            VideoFragment videoFragment = new VideoFragment();
            if (videoUrl.equals("") || steps.getVideoUrl() == null) {
                videoFragment.setVideoUrl(null);

            } else {
                videoFragment.setVideoUrl(videoUrl);
            }
            videoFragment.setCurrentSteps(steps);
            videoFragment.setDescription(desc);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frag_player_container, videoFragment)
                    .commit();
        }
    }

}


