package ke.co.keki.com.keki.model;

import java.util.List;

public class Pastry {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<Steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    public int getServings() {
        return Servings;
    }

    public void setServings(int servings) {
        Servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
