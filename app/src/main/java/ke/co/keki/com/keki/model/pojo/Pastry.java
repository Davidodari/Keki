package ke.co.keki.com.keki.model.pojo;

import java.util.List;

public class Pastry implements IPastry {

    //Pastry Object Pojo
    private int id;
    private String name;
    private List<Ingredients> ingredientsList;
    private List<Steps> stepsList;
    private int Servings;
    private String image;

    public Pastry() {

    }

    public Pastry(int id, String name, List<Ingredients> ingredientsList, List<Steps> stepsList, int Servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
        this.Servings = Servings;
        this.image = image;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    @Override
    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public List<Steps> getStepsList() {
        return stepsList;
    }

    @Override
    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    @Override
    public int getServings() {
        return Servings;
    }

    @Override
    public void setServings(int servings) {
        Servings = servings;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }
}
