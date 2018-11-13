package ke.co.keki.com.keki.view.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.PastryConstants;

public class VideoStepsActivity extends AppCompatActivity {

    //Frame Layout Holding Player and Description Views
    @BindView(R.id.frag_player_container)
    FrameLayout playerContainer;
    //Fragment Manager Carrying Out Fragment Transactions
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_steps);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //Check If Intent has Extras
        if (intent.getExtras() != null) {
            //If intent has a step object retrieve its contents
            if (intent.hasExtra(PastryConstants.STEPS_OBJECT)) {
                if (savedInstanceState == null) {
                    //If theres no instance of fragment recreate it
                    Steps step = Parcels.unwrap(intent.getParcelableExtra(PastryConstants.STEPS_OBJECT));
                    String desc = step.getDescription();
                    String videoUrl = step.getVideoUrl();
                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setDescription(desc);
                    videoFragment.setCurrentSteps(step);
                    //If video url is null set it to null otherwise set link
                    if (videoUrl.equals("") || step.getVideoUrl() == null) {
                        videoFragment.setVideoUrl(null);
                    } else {
                        videoFragment.setVideoUrl(videoUrl);
                    }
                    //Add Fragment To Activity
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.frag_player_container, videoFragment)
                            .commit();


                }
            }
        }
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
