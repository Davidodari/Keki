package ke.co.keki.com.keki.view.steps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Steps> stepsList;
    private static IStepsClickHandler iStepsClickHandler;

    //Adapter takes in presenter to handle data operations
    StepsAdapter(List<Steps> stepsList,IStepsClickHandler iStepsClickHandler) {
        this.stepsList = stepsList;
        StepsAdapter.iStepsClickHandler = iStepsClickHandler;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutId = R.layout.steps_view_list_item;
        final boolean shouldAttachToParremtImmediately = false;
        Context context = viewGroup.getContext();
        LayoutInflater pastryInflater = LayoutInflater.from(context);
        View view = pastryInflater.inflate(layoutId, viewGroup, shouldAttachToParremtImmediately);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder stepsViewHolder, int i) {
        stepsViewHolder.bind(stepsList.get(i));
    }

    @Override
    public int getItemCount() {
        return (stepsList == null) ? 0 : stepsList.size();
    }


    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Bind Views
        @BindView(R.id.iv_thumbnail)
        ImageView mStepImageView;
        @BindView(R.id.tv_id)
        TextView mStepIdTextView;
        @BindView(R.id.tv_short_desc)
        TextView mStepShortDescTextView;

        StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Steps steps) {
            if (steps.getThumbnailUrl() != null && !steps.getThumbnailUrl().equals("")) {
                Picasso.get().load(steps.getThumbnailUrl()).error(R.drawable.donut).into(mStepImageView);
            } else {
                mStepImageView.setImageResource(R.drawable.donut);
            }
            String id = String.format(Locale.getDefault(), "%d", steps.getId());
            mStepIdTextView.setText(id);
            mStepShortDescTextView.setText(steps.getDescription());


        }

        @Override
        public void onClick(View v) {
            iStepsClickHandler.onStepClicked(stepsList.get(getAdapterPosition()), v);
        }
    }

    interface IStepsClickHandler {
        void onStepClicked(Steps steps, View v);
    }
}
