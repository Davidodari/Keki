package ke.co.keki.com.keki.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Pastry Object POJO Class
 */
@Entity(tableName = "pastry")
@Parcel(Parcel.Serialization.BEAN)
public class Pastry implements IPastry {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "ingredients")
    private List<Ingredients> ingredientsList;

    @Ignore
    private List<Steps> stepsList;

    @ColumnInfo(name = "servings")
    private int Servings;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public Pastry() {

    }

    @Ignore
    public Pastry(int id, String name, List<Ingredients> ingredientsList, List<Steps> stepsList, int Servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
        this.Servings = Servings;
        this.image = image;
    }

    @Ignore
    public Pastry(int id, String name, int Servings, String image) {
        this.id = id;
        this.name = name;
        this.Servings = Servings;
        this.image = image;
    }

    public Pastry(int id, String name, List<Ingredients> ingredientsList, int Servings, String image,Date updatedAt) {
        this.updatedAt = updatedAt;
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.Servings = Servings;
        this.image = image;
    }

    @Ignore
    public Pastry(String name, int Servings, String image) {
        this.name = name;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
