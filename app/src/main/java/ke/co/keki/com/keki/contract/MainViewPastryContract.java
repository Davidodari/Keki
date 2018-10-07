package ke.co.keki.com.keki.contract;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface MainViewPastryContract {
    interface Model {

        List<Pastry> getPastryData();

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

        void onBindPastryViewAtPosition(int position, MainViewPastryContract.View.RecyclerViewData recyclerViewSetup);

        int getPastryItemCount();

        void onCardSelected(Pastry pastry);

        void onDestroy();
    }
}
