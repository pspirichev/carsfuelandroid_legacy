package newways.cars_fuel_android;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Ref;
import java.util.List;

@Dao
public interface RefillDao
{
    @Insert
    void insert(Refill refill);

    @Update
    void update(Refill refill);

    @Delete
    void delete(Refill refill);

    @Query("SELECT * FROM refill")
    List<Refill> getAll();

    @Query("SELECT * FROM refill WHERE id = :id")
    Refill getById(long id);
}
