package ke.co.keki.com.keki.model;


import java.util.ArrayList;
import java.util.List;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;


public class MainViewPastryModel implements MainViewPastryContract.Model {
    private static final String TAG = MainViewPastryModel.class.getSimpleName();
    List<Pastry> mPastriesList;

    @Override
    public List<Pastry> getPastryData() {
        //Mock Data
        final Pastry pastryOne = new Pastry(1, "Cake", 6, null);
        final Pastry pastryTwo = new Pastry(2, "Pie", 8, null);
        final Pastry pastryThree = new Pastry(3, "Donought", 3, null);
        mPastriesList = new ArrayList<Pastry>() {{
            add(pastryOne);
            add(pastryThree);
            add(pastryTwo);

        }};
        return mPastriesList;
    }
}
