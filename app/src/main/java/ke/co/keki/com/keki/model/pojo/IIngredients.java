package ke.co.keki.com.keki.model.pojo;

interface IIngredients {
    int getQuantity();

    void setQuantity(int quantity);

    String getMeasure();

    void setMeasure(String measure);

    String getIngredientItem();

    void setIngredientItem(String ingredientItem);

    void setId(int id);

    void setIngredient_id(int ingredient_id);

    int getIngredient_id();

    int getId();
}
