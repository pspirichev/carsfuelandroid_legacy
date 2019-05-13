package newways.cars_fuel_android;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class CarDao_Impl implements CarDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCar;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCar;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCar;

  public CarDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCar = new EntityInsertionAdapter<Car>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Car`(`id`,`name`,`make`,`year`,`mileage`,`fuel`,`fuelAbbr`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Car value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getMake() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMake());
        }
        stmt.bindLong(4, value.getYear());
        stmt.bindLong(5, value.getMileage());
        if (value.getFuel() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFuel());
        }
        if (value.getFuelAbbr() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getFuelAbbr());
        }
      }
    };
    this.__deletionAdapterOfCar = new EntityDeletionOrUpdateAdapter<Car>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Car` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Car value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCar = new EntityDeletionOrUpdateAdapter<Car>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Car` SET `id` = ?,`name` = ?,`make` = ?,`year` = ?,`mileage` = ?,`fuel` = ?,`fuelAbbr` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Car value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getMake() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMake());
        }
        stmt.bindLong(4, value.getYear());
        stmt.bindLong(5, value.getMileage());
        if (value.getFuel() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFuel());
        }
        if (value.getFuelAbbr() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getFuelAbbr());
        }
        stmt.bindLong(8, value.getId());
      }
    };
  }

  @Override
  public void insert(Car car) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCar.insert(car);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Car car) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCar.handle(car);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Car car) {
    __db.beginTransaction();
    try {
      __updateAdapterOfCar.handle(car);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Car> getAll() {
    final String _sql = "SELECT * FROM car";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfMake = _cursor.getColumnIndexOrThrow("make");
      final int _cursorIndexOfYear = _cursor.getColumnIndexOrThrow("year");
      final int _cursorIndexOfMileage = _cursor.getColumnIndexOrThrow("mileage");
      final int _cursorIndexOfFuel = _cursor.getColumnIndexOrThrow("fuel");
      final int _cursorIndexOfFuelAbbr = _cursor.getColumnIndexOrThrow("fuelAbbr");
      final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Car _item;
        _item = new Car();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpMake;
        _tmpMake = _cursor.getString(_cursorIndexOfMake);
        _item.setMake(_tmpMake);
        final short _tmpYear;
        _tmpYear = _cursor.getShort(_cursorIndexOfYear);
        _item.setYear(_tmpYear);
        final int _tmpMileage;
        _tmpMileage = _cursor.getInt(_cursorIndexOfMileage);
        _item.setMileage(_tmpMileage);
        final String _tmpFuel;
        _tmpFuel = _cursor.getString(_cursorIndexOfFuel);
        _item.setFuel(_tmpFuel);
        final String _tmpFuelAbbr;
        _tmpFuelAbbr = _cursor.getString(_cursorIndexOfFuelAbbr);
        _item.setFuelAbbr(_tmpFuelAbbr);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Car getById(long id) {
    final String _sql = "SELECT * FROM car WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfMake = _cursor.getColumnIndexOrThrow("make");
      final int _cursorIndexOfYear = _cursor.getColumnIndexOrThrow("year");
      final int _cursorIndexOfMileage = _cursor.getColumnIndexOrThrow("mileage");
      final int _cursorIndexOfFuel = _cursor.getColumnIndexOrThrow("fuel");
      final int _cursorIndexOfFuelAbbr = _cursor.getColumnIndexOrThrow("fuelAbbr");
      final Car _result;
      if(_cursor.moveToFirst()) {
        _result = new Car();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
        final String _tmpMake;
        _tmpMake = _cursor.getString(_cursorIndexOfMake);
        _result.setMake(_tmpMake);
        final short _tmpYear;
        _tmpYear = _cursor.getShort(_cursorIndexOfYear);
        _result.setYear(_tmpYear);
        final int _tmpMileage;
        _tmpMileage = _cursor.getInt(_cursorIndexOfMileage);
        _result.setMileage(_tmpMileage);
        final String _tmpFuel;
        _tmpFuel = _cursor.getString(_cursorIndexOfFuel);
        _result.setFuel(_tmpFuel);
        final String _tmpFuelAbbr;
        _tmpFuelAbbr = _cursor.getString(_cursorIndexOfFuelAbbr);
        _result.setFuelAbbr(_tmpFuelAbbr);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
