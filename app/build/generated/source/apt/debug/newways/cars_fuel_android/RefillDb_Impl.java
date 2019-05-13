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

public class RefillDb_Impl extends RefillDb {
  private volatile RefillDao _refillDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Refill` (`id` INTEGER NOT NULL, `date` INTEGER NOT NULL, `volume` INTEGER NOT NULL, `cost` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"78ac9610e567315896f04856ab0ee741\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Refill`");
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
        final HashMap<String, TableInfo.Column> _columnsRefill = new HashMap<String, TableInfo.Column>(4);
        _columnsRefill.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsRefill.put("date", new TableInfo.Column("date", "INTEGER", true, 0));
        _columnsRefill.put("volume", new TableInfo.Column("volume", "INTEGER", true, 0));
        _columnsRefill.put("cost", new TableInfo.Column("cost", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRefill = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRefill = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRefill = new TableInfo("Refill", _columnsRefill, _foreignKeysRefill, _indicesRefill);
        final TableInfo _existingRefill = TableInfo.read(_db, "Refill");
        if (! _infoRefill.equals(_existingRefill)) {
          throw new IllegalStateException("Migration didn't properly handle Refill(newways.cars_fuel_android.Refill).\n"
                  + " Expected:\n" + _infoRefill + "\n"
                  + " Found:\n" + _existingRefill);
        }
      }
    }, "78ac9610e567315896f04856ab0ee741");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Refill");
  }

  @Override
  public RefillDao refillDao() {
    if (_refillDao != null) {
      return _refillDao;
    } else {
      synchronized(this) {
        if(_refillDao == null) {
          _refillDao = new RefillDao_Impl(this);
        }
        return _refillDao;
      }
    }
  }
}
