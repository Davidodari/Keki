package ke.co.keki.com.keki.model.pojo;

import java.util.List;

interface IPastry {
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
