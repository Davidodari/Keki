package ke.co.keki.com.keki.contract;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;

public interface PastryDetailsContract {
    interface Model {

    }

    interface View {
        void bindViews(@NonNull Pastry pastry);

        void openStepView(List<Steps> stepsList);
    }

    interface Presenter {
        void onStart(@NonNull Intent intent);

        void onClicked(List<Steps> stepsList);
    }
}
