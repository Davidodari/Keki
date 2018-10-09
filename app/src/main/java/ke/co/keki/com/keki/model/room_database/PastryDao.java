package ke.co.keki.com.keki.model.room_database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ke.co.keki.com.keki.model.pojo.Pastry;

@Dao
public interface PastryDao {
    @Query("SELECT * FROM pastry ORDER BY name")
    List<Pastry> loadPastires();

    @Insert
    void insertPastries(Pastry pastry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePastry(Pastry pastry);

}
