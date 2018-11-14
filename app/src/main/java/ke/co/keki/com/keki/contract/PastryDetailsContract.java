package ke.co.keki.com.keki.contract;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.model.room_database.PastryDatabase;

public interface PastryDetailsContract {

    interface View {
        void bindViews(@NonNull Pastry pastry);

        void openStepView(List<Steps> stepsList);

        void displayOnAddToast();

        void displayOnRemoveToast();
    }

    interface Presenter {

        void onStart(@NonNull Intent intent);

        void onClicked(List<Steps> stepsList);

        void databaseOperations(Pastry pastry);

        boolean checkFromDatabase(Pastry pastry);

    }
}
