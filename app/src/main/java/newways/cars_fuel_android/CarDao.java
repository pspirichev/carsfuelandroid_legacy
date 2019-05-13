package newways.cars_fuel_android;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CarDao
{
    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Query("SELECT * FROM car")
    List<Car> getAll();

    @Query("SELECT * FROM car WHERE id = :id")
    Car getById(long id);
}
