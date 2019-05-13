package newways.cars_fuel_android;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Refill.class}, version = 1, exportSchema = false)
public abstract class RefillDb extends RoomDatabase {
    public abstract RefillDao refillDao();
}
