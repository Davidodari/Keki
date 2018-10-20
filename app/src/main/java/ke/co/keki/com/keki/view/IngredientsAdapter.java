package ke.co.keki.com.keki.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Ingredients;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredients> mIngredients;

    public IngredientsAdapter(List<Ingredients> mIngredients) {
        this.mIngredients = mIngredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater ingredientInflater = LayoutInflater.from(context);
        int layoutId = R.layout.ingredient_list_item;
        View viewIngredients = ingredientInflater.inflate(layoutId, viewGroup, false);
        return new IngredientsViewHolder(viewIngredients);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int i) {
        ingredientsViewHolder.bindViews(mIngredients.get(i));
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient_name)
        TextView ingredientItemNameTextView;
        @BindView(R.id.tv_ingredient_measure)
        TextView ingredientMeasure;
        @BindView(R.id.tv_ingredient_quantity)
        TextView ingredientQuantity;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindViews(Ingredients ingredients) {
            ingredientItemNameTextView.setText(ingredients.getIngredientItem());
            ingredientMeasure.setText(ingredients.getMeasure());
            String quantity = String.format(Locale.getDefault(), "%d", ingredients.getQuantity());
            ingredientQuantity.setText(quantity);
        }
    }
}
