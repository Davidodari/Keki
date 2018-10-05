package ke.co.keki.com.keki.model;

public class Ingredients {

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientItem() {
        return ingredientItem;
    }

    public void setIngredientItem(String ingredientItem) {
        this.ingredientItem = ingredientItem;
    }
}
