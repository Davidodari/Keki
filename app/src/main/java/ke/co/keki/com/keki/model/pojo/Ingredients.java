package ke.co.keki.com.keki.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
@Entity(tableName = "ingredients")
public class Ingredients implements IIngredients {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "ingredient_id")
    private int ingredient_id;
    //Ingredients Item json objects
    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "measure")
    private String measure;

    @ColumnInfo(name = "ingredient_name")
    private String ingredientItem;

    @Ignore
    public Ingredients() {
    }

    @Ignore
    public Ingredients(int quantity, String measure, String ingredientItem) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientItem = ingredientItem;
    }

    public Ingredients(int ingredient_id, int quantity, String measure, String ingredientItem) {
        this.ingredient_id = ingredient_id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }
}
