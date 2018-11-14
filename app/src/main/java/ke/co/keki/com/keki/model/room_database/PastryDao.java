package ke.co.keki.com.keki.model.room_database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

@Dao
public interface PastryDao {

    @Query("SELECT * FROM pastry ORDER BY name")
    List<Pastry> loadPastry();

    @Insert
    void insertPastry(Pastry pastry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePastry(Pastry pastry);

    @Delete
    void removePastry(Pastry pastry);

    @Query("SELECT * FROM pastry WHERE id = :id")
    List<Pastry> loadPastryById(int id);
}
