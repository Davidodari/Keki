package ke.co.keki.com.keki.model;


import android.util.Log;

import java.util.List;

import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.model.pojo.Pastry;
import ke.co.keki.com.keki.utils.JsonUtils;


public class MainViewPastryModel implements MainViewPastryContract.Model {
    private static final String TAG = MainViewPastryModel.class.getSimpleName();
    List<Pastry> mPastriesList;
    List<Pastry> testList;


    @Override
    public void setPastryData(String jsonResponse) {
        mPastriesList = JsonUtils.parseJSON(jsonResponse);
        Log.d(TAG, "" + mPastriesList.size());
        storeModelData(mPastriesList);
    }


    @Override
    public List<Pastry> getPastryData() {
        //1.TODO Retrieve Data From Database as list and display
        return testList;
    }

    public void storeModelData(List<Pastry> mPastriesList) {
        for (int i = 0; i < mPastriesList.size(); i++) {
            String name = mPastriesList.get(i).getName();
            String imageLink = mPastriesList.get(i).getImage();
            int serving = mPastriesList.get(i).getServings();
        }
    }


}
