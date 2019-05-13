package newways.cars_fuel_android;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class CarDb_Impl extends CarDb {
  private volatile CarDao _carDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Car` (`id` INTEGER NOT NULL, `name` TEXT, `make` TEXT, `year` INTEGER NOT NULL, `mileage` INTEGER NOT NULL, `fuel` TEXT, `fuelAbbr` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"ca8e5109f766fc49b269033abd84ed29\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Car`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCar = new HashMap<String, TableInfo.Column>(7);
        _columnsCar.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsCar.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsCar.put("make", new TableInfo.Column("make", "TEXT", false, 0));
        _columnsCar.put("year", new TableInfo.Column("year", "INTEGER", true, 0));
        _columnsCar.put("mileage", new TableInfo.Column("mileage", "INTEGER", true, 0));
        _columnsCar.put("fuel", new TableInfo.Column("fuel", "TEXT", false, 0));
        _columnsCar.put("fuelAbbr", new TableInfo.Column("fuelAbbr", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCar = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCar = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCar = new TableInfo("Car", _columnsCar, _foreignKeysCar, _indicesCar);
        final TableInfo _existingCar = TableInfo.read(_db, "Car");
        if (! _infoCar.equals(_existingCar)) {
          throw new IllegalStateException("Migration didn't properly handle Car(newways.cars_fuel_android.Car).\n"
                  + " Expected:\n" + _infoCar + "\n"
                  + " Found:\n" + _existingCar);
        }
      }
    }, "ca8e5109f766fc49b269033abd84ed29");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Car");
  }

  @Override
  public CarDao carDao() {
    if (_carDao != null) {
      return _carDao;
    } else {
      synchronized(this) {
        if(_carDao == null) {
          _carDao = new CarDao_Impl(this);
        }
        return _carDao;
      }
    }
  }
}
