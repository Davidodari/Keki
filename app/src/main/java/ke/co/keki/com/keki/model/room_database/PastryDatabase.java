package ke.co.keki.com.keki.model.room_database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import ke.co.keki.com.keki.model.pojo.Pastry;

@Database(entities = {Pastry.class}, version = 1, exportSchema = false)
public abstract class PastryDatabase extends RoomDatabase {
    private static PastryDatabase pastryDatabaseInstance;
    private static final String LOG_TAG = PastryDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "pastry_database";

    public static PastryDatabase getInstance(Context context) {
        if (pastryDatabaseInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "New Db Instance");
                pastryDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), PastryDatabase.class, PastryDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

            }
        }
        Log.d(LOG_TAG, "Getting Db INstance");
        return pastryDatabaseInstance;
    }

    public abstract PastryDao pastryDao();
}
