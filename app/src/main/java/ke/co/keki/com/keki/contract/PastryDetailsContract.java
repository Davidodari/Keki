package ke.co.keki.com.keki.contract;

import android.content.Intent;
import android.support.annotation.NonNull;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface PastryDetailsContract {
    interface Model {

    }

    interface View {
        void bindViews(@NonNull Pastry pastry);
    }

    interface Presenter {
        void onStart(@NonNull Intent intent);
    }
}
