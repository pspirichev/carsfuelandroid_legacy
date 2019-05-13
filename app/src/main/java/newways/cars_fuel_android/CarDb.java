package newways.cars_fuel_android;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Car.class}, version = 1, exportSchema = false)
public abstract class CarDb extends RoomDatabase {
    public abstract CarDao carDao();
}
