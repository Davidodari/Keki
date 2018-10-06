package ke.co.keki.com.keki.model;

import java.util.List;

public interface IPastry {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    List<Ingredients> getIngredientsList();

    void setIngredientsList(List<Ingredients> ingredientsList);

    List<Steps> getStepsList();

    void setStepsList(List<Steps> stepsList);

    int getServings();

    void setServings(int servings);

    String getImage();

    void setImage(String image);
}
