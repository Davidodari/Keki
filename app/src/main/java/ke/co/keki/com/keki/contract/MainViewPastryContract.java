package ke.co.keki.com.keki.contract;

import android.net.Uri;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface MainViewPastryContract {
    interface Model {

        List<Pastry> getPastryData();

        void setPastryData();
    }

    interface View {

        void displayError();

        void displayAnimations();

        void diplayLoadAnimations();

        //Recycler View View Interface
        interface RecyclerViewData {
            void setPastryName(String pastryName);

            void setPastryImage(String pastryImageLink);

            void setPastryServing(int pastryServing);
        }

        void openIntent();
    }

    interface Presenter {
        void onViewInit(List<Pastry> mListPastry);

        void onCardSelected(Pastry pastry);

        void onDestroy();
    }
}
