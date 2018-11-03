package ke.co.keki.com.keki.contract;

import ke.co.keki.com.keki.model.pojo.Steps;

public interface StepsContract {

    interface Model {

    }

    interface View {
        void openVideoFragment(Steps steps);
    }

    interface Presenter {
        void onStepsClicked();
    }
}
