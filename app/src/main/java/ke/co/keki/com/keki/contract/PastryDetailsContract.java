package ke.co.keki.com.keki.contract;

import android.content.Intent;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface PastryDetailsContract {
    interface Model {

    }

    interface View {
        void bindViews(Pastry pastry);
    }

    interface Presenter {
        void onStart(Intent intent);
    }
}
