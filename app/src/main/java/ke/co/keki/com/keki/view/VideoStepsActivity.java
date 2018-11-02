package ke.co.keki.com.keki.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.PastryConstants;

public class VideoStepsActivity extends AppCompatActivity {

    @BindView(R.id.tv_description)
    TextView textViewDescription;
    @BindView(R.id.frag_player_container)
    FrameLayout playerContainer;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_steps);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.hasExtra(PastryConstants.STEPS_OBJECT)) {
                Steps step = Parcels.unwrap(intent.getParcelableExtra(PastryConstants.STEPS_OBJECT));
                Log.d("VideoStepsActivity", "stepDesc:" + step.getDescription());
                String desc = step.getDescription();
                String videoUrl = step.getVideoUrl();
                VideoFragment videoFragment = new VideoFragment();
                if (videoUrl.equals("") || step.getVideoUrl() == null) {
                    playerContainer.setVisibility(View.GONE);
                } else {
                    videoFragment.setVideoUrl(videoUrl);
                    videoFragment.setCurrentSteps(step);
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.frag_player_container, videoFragment)
                            .commit();
                }
                if (textViewDescription != null) {
                    textViewDescription.setText(desc);
                }
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }


//    @SuppressLint("InlinedApi")
//    private void hideSystemUi() {
//        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }

}
