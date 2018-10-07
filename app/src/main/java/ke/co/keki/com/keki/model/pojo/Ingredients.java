package ke.co.keki.com.keki.model.pojo;

public class Ingredients implements IIngredients {

    //Ingredients Item json objects
    private int quantity;
    private String measure;
    private String ingredientItem;

    public Ingredients() {

    }

    public Ingredients(int quantity, String measure, String ingredientItem) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientItem = ingredientItem;

    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getMeasure() {
        return measure;
    }

    @Override
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String getIngredientItem() {
        return ingredientItem;
    }

    @Override
    public void setIngredientItem(String ingredientItem) {
        this.ingredientItem = ingredientItem;
    }
}
