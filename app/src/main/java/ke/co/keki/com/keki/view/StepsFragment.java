package ke.co.keki.com.keki.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.PastryConstants;

public class StepsFragment extends Fragment {

    String TAG = StepsFragment.class.getSimpleName();
    List<Steps> steps;
    @BindView(R.id.rv_steps)
    RecyclerView stepsRecyclerView;
    GridLayoutManager gridLayoutManager;
    StepsAdapter stepsAdapter;

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public StepsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        ButterKnife.bind(this, rootView);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(inflater.getContext(), DividerItemDecoration.VERTICAL);
        gridLayoutManager = new GridLayoutManager(inflater.getContext(), PastryConstants.calculateNoOfColumns(inflater.getContext()));
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(gridLayoutManager);
        stepsRecyclerView.addItemDecoration(itemDecoration);
        if (steps != null) {
            stepsAdapter = new StepsAdapter(steps);
            stepsRecyclerView.setAdapter(stepsAdapter);
        } else {
            Log.d(TAG, "Null List");
        }
        return rootView;
    }
}
