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

public class RefillDao_Impl implements RefillDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRefill;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfRefill;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfRefill;

  public RefillDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRefill = new EntityInsertionAdapter<Refill>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Refill`(`id`,`date`,`volume`,`cost`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Refill value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getDate());
        stmt.bindLong(3, value.getVolume());
        stmt.bindLong(4, value.getCost());
      }
    };
    this.__deletionAdapterOfRefill = new EntityDeletionOrUpdateAdapter<Refill>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Refill` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Refill value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfRefill = new EntityDeletionOrUpdateAdapter<Refill>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Refill` SET `id` = ?,`date` = ?,`volume` = ?,`cost` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Refill value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getDate());
        stmt.bindLong(3, value.getVolume());
        stmt.bindLong(4, value.getCost());
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void insert(Refill refill) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRefill.insert(refill);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Refill refill) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfRefill.handle(refill);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Refill refill) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRefill.handle(refill);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Refill> getAll() {
    final String _sql = "SELECT * FROM refill";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
      final int _cursorIndexOfVolume = _cursor.getColumnIndexOrThrow("volume");
      final int _cursorIndexOfCost = _cursor.getColumnIndexOrThrow("cost");
      final List<Refill> _result = new ArrayList<Refill>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Refill _item;
        _item = new Refill();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        _item.setDate(_tmpDate);
        final short _tmpVolume;
        _tmpVolume = _cursor.getShort(_cursorIndexOfVolume);
        _item.setVolume(_tmpVolume);
        final short _tmpCost;
        _tmpCost = _cursor.getShort(_cursorIndexOfCost);
        _item.setCost(_tmpCost);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Refill getById(long id) {
    final String _sql = "SELECT * FROM refill WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
      final int _cursorIndexOfVolume = _cursor.getColumnIndexOrThrow("volume");
      final int _cursorIndexOfCost = _cursor.getColumnIndexOrThrow("cost");
      final Refill _result;
      if(_cursor.moveToFirst()) {
        _result = new Refill();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        _result.setDate(_tmpDate);
        final short _tmpVolume;
        _tmpVolume = _cursor.getShort(_cursorIndexOfVolume);
        _result.setVolume(_tmpVolume);
        final short _tmpCost;
        _tmpCost = _cursor.getShort(_cursorIndexOfCost);
        _result.setCost(_tmpCost);
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
