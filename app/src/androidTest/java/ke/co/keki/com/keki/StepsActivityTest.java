package ke.co.keki.com.keki;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import ke.co.keki.com.keki.view.steps.StepsActivity;

@RunWith(AndroidJUnit4.class)
public class StepsActivityTest {

    @Rule
    public ActivityTestRule<StepsActivity> mActivityRule =
            new ActivityTestRule<>(StepsActivity.class);
}
