package ke.co.keki.com.keki.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.contract.MainViewPastryContract;
import ke.co.keki.com.keki.presenter.MainViewPastryPresenter;

public class PastryAdapter extends RecyclerView.Adapter<PastryAdapter.PastryAdapterViewHolder> {

    private final MainViewPastryPresenter presenter;

    //Adapter takes in presenter to handle data operations
    public PastryAdapter(MainViewPastryPresenter mainViewPastryPresenter) {
        this.presenter = mainViewPastryPresenter;
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
        presenter.onBindPastryViewAtPosition(i, pastryAdapterViewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.getPastryItemCount();
    }


    class PastryAdapterViewHolder extends RecyclerView.ViewHolder implements MainViewPastryContract.View.RecyclerViewData {

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

        //Recycler View View interface implementation
        @Override
        public void setPastryName(String pastryName) {

            mPastryTitleTextView.setText(pastryName);
        }

        @Override
        public void setPastryImage(String pastryImageLink) {

            Picasso.get()
                    .load(pastryImageLink)
                    .placeholder(R.drawable.donut)
                    .into(mPastryImageView);
        }

        @Override
        public void setPastryServing(int pastryServing) {

            String serveValue = String.format("%d", pastryServing);
            mPastryServingValueTextView.setText(serveValue);
        }
    }
}
