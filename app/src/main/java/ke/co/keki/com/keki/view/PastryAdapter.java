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

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Pastry;

public class PastryAdapter extends RecyclerView.Adapter<PastryAdapter.PastryAdapterViewHolder> {

    private List<Pastry> mainViewPastryList;
    private static IPastryClickHandler iPastryClickHandler;

    //Adapter takes in presenter to handle data operations
    PastryAdapter(List<Pastry> mainViewPastryList, IPastryClickHandler iPastryClickHandlerParams) {
        this.mainViewPastryList = mainViewPastryList;
        iPastryClickHandler = iPastryClickHandlerParams;
    }

    @NonNull
    @Override
    public PastryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutId = R.layout.main_view_list_item;
        final boolean shouldAttachToParremtImmediately = false;
        Context context = viewGroup.getContext();
        LayoutInflater pastryInflater = LayoutInflater.from(context);
        View view = pastryInflater.inflate(layoutId, viewGroup, shouldAttachToParremtImmediately);
        return new PastryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastryAdapterViewHolder pastryAdapterViewHolder, int i) {
        pastryAdapterViewHolder.bind(mainViewPastryList.get(i));
    }

    @Override
    public int getItemCount() {
        return (mainViewPastryList == null) ? 0 : mainViewPastryList.size();
    }


    class PastryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Bind Views
        @BindView(R.id.iv_pastry_image)
        ImageView mPastryImageView;
        @BindView(R.id.tv_pastry_title)
        TextView mPastryTitleTextView;
        @BindView(R.id.tv_no_of_servings)
        TextView mPastryServingValueTextView;

        PastryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Pastry pastry) {
            mPastryTitleTextView.setText(pastry.getName());
            if (pastry.getImage().equals("")) {
                mPastryImageView.setImageDrawable(itemView.getContext().getDrawable(R.drawable.donut));
            } else {
                Picasso.get().load(pastry.getImage()).into(mPastryImageView);
            }
            String serveValue = String.format(Locale.getDefault(), "%d", pastry.getServings());
            mPastryServingValueTextView.setText(serveValue);
        }

        @Override
        public void onClick(View v) {
            iPastryClickHandler.onPastryCardClicked(mainViewPastryList.get(getAdapterPosition()), v);
        }
    }

    interface IPastryClickHandler {
        void onPastryCardClicked(Pastry pastry, View v);
    }
}
