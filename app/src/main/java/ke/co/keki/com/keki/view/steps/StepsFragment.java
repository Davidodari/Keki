package ke.co.keki.com.keki.view.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.PastryConstants;
import ke.co.keki.com.keki.view.DetailActivity;

public class StepsFragment extends Fragment implements StepsAdapter.IStepsClickHandler {

    String TAG = StepsFragment.class.getSimpleName();
    private List<Steps> stepsList;
    @BindView(R.id.rv_steps)
    RecyclerView stepsRecyclerView;
    GridLayoutManager gridLayoutManager;
    StepsAdapter stepsAdapter;
    OnStepClickedListener callback;

    //will call method in host activity when a step is selected
    public interface OnStepClickedListener {
        void onStepSelected(Steps steps, int stepId);
    }

    //checks that hst has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnStepClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnStepClickedListener");

        }
    }

    public StepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Define View
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        ButterKnife.bind(this, rootView);
        setStepsList(DetailActivity.steps);
        if (savedInstanceState != null) {
            Log.d(TAG, "hasSavedInstance");
            stepsList = Parcels.unwrap(savedInstanceState.getParcelable(PastryConstants.PASTRY_LIST));
        }
        gridLayoutManager = new GridLayoutManager(inflater.getContext(), PastryConstants.calculateNoOfColumns(inflater.getContext()));
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(gridLayoutManager);
        if (stepsList != null) {
            Log.d(TAG, "" + stepsList.size());
            stepsAdapter = new StepsAdapter(stepsList, this);
            stepsRecyclerView.setAdapter(stepsAdapter);
        } else {
            Log.d(TAG, "Null List");
        }
        return rootView;
    }

    //On clicked passed id of step that was clicked
    @Override
    public void onStepClicked(Steps steps, View v) {
        callback.onStepSelected(steps,steps.getId());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PastryConstants.PASTRY_LIST, Parcels.wrap(stepsList));
    }

    //Set The List Of Steps To Display
    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

}
