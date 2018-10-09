package ke.co.keki.com.keki.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Pastry;

public class PastryAdapter extends RecyclerView.Adapter<PastryAdapter.PastryAdapterViewHolder> {

    private static String TAG = PastryAdapter.class.getSimpleName();
    private List<Pastry> mainViewPastryList;

    //Adapter takes in presenter to handle data operations
    public PastryAdapter(List<Pastry> mainViewPastryList) {
        this.mainViewPastryList = mainViewPastryList;
    }

    @NonNull
    @Override
    public PastryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutId = R.layout.main_view_list_item;
        boolean shouldAttachToParremtImmediately = false;
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutId, viewGroup, shouldAttachToParremtImmediately);
        return new PastryAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PastryAdapterViewHolder pastryAdapterViewHolder, int i) {
        pastryAdapterViewHolder.bind(mainViewPastryList.get(i));
        Log.d(TAG, mainViewPastryList.get(0).getName());
    }

    @Override
    public int getItemCount() {
        return (mainViewPastryList == null) ? 0 : mainViewPastryList.size();
    }


    class PastryAdapterViewHolder extends RecyclerView.ViewHolder {

        //Bind Views
        @BindView(R.id.iv_pastry_image)
        ImageView mPastryImageView;
        @BindView(R.id.tv_pastry_title)
        TextView mPastryTitleTextView;
        @BindView(R.id.tv_no_of_servings)
        TextView mPastryServingValueTextView;
        @BindView(R.id.tv_servings_title)
        TextView mServingTitle;


        public PastryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Pastry pastry) {
            mPastryTitleTextView.setText(pastry.getName());
            mPastryImageView.setImageDrawable(itemView.getContext().getDrawable(R.drawable.donut));
            String serveValue = String.format(Locale.getDefault(), "%d", pastry.getServings());
            mPastryServingValueTextView.setText(serveValue);
        }

    }
}
