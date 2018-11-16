package ke.co.keki.com.keki.contract;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.model.pojo.Steps;

public interface PastryDetailsContract {

    interface View {
        void bindViews(@NonNull Pastry pastry);

        void openStepView(List<Steps> stepsList);

        void onAddedToDatabase();

        void onRemovedFromDatabase();
    }

    interface Presenter {

        void onStart(Pastry pastry);

        void onClicked(List<Steps> stepsList);

        void databaseOperations(Context ctx, Pastry pastry);

        void checkFromDatabase(Pastry pastry);

        void addPreferenceAndUpdateWidget(Context ctx, Pastry pastry);

        void removePreferenceAndUpdateWidget(Context ctx);

    }
}
