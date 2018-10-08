package ke.co.keki.com.keki.contract;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

public interface MainViewPastryContract {
    interface Model {


        void setPastryData(String jsonObject);

        List<Pastry> getPastryData();
    }

    interface View {

        void displayError();

        void displayOnLoadError();

        void displayAnimations();

        void displayLoadAnimations();

        void displayJson(String string);

        interface RecyclerViewData {
            //Recycler View View Interface
            void setPastryName(String pastryName);

            void setPastryImage(String pastryImageLink);

            void setPastryServing(int pastryServing);
        }
    }

    interface Presenter {
        //called on onCreate of view
        void onStart();

        //binds each view in recycler view adapter
        void onBindPastryViewAtPosition(int position, MainViewPastryContract.View.RecyclerViewData recyclerViewSetup);

        //gets total no of items in pastry list
        int getPastryItemCount();

        //called on onDestroy of view
        void onDestroy();

        void setPastryData(String  response);
    }
}
