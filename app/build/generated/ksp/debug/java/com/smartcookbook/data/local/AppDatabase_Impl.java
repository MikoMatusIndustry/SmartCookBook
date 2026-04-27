package com.smartcookbook.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile FavoriteDao _favoriteDao;

  private volatile ShoppingItemDao _shoppingItemDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `Kategorie` (`id` INTEGER NOT NULL, `nazwa` TEXT NOT NULL, `obrazek_url` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `Przepisy` (`id` INTEGER NOT NULL, `kategoria_id` INTEGER NOT NULL, `nazwa` TEXT NOT NULL, `czas_przygotowania` TEXT NOT NULL, `instrukcje` TEXT NOT NULL, `obrazek_url` TEXT NOT NULL, `wideo_url` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_Przepisy_kategoria_id` ON `Przepisy` (`kategoria_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `Skladniki` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `przepis_id` INTEGER NOT NULL, `nazwa` TEXT NOT NULL, `ilosc` TEXT NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_Skladniki_przepis_id` ON `Skladniki` (`przepis_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `Ulubione` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_uid` TEXT NOT NULL, `przepis_id` INTEGER NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_Ulubione_przepis_id` ON `Ulubione` (`przepis_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `Lista_Zakupow` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_uid` TEXT NOT NULL, `nazwa_produktu` TEXT NOT NULL, `czy_kupione` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3250803e76e9b564c7a0bcfda974d421')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `Kategorie`");
        db.execSQL("DROP TABLE IF EXISTS `Przepisy`");
        db.execSQL("DROP TABLE IF EXISTS `Skladniki`");
        db.execSQL("DROP TABLE IF EXISTS `Ulubione`");
        db.execSQL("DROP TABLE IF EXISTS `Lista_Zakupow`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsKategorie = new HashMap<String, TableInfo.Column>(3);
        _columnsKategorie.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKategorie.put("nazwa", new TableInfo.Column("nazwa", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKategorie.put("obrazek_url", new TableInfo.Column("obrazek_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysKategorie = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesKategorie = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoKategorie = new TableInfo("Kategorie", _columnsKategorie, _foreignKeysKategorie, _indicesKategorie);
        final TableInfo _existingKategorie = TableInfo.read(db, "Kategorie");
        if (!_infoKategorie.equals(_existingKategorie)) {
          return new RoomOpenHelper.ValidationResult(false, "Kategorie(com.smartcookbook.data.model.Category).\n"
                  + " Expected:\n" + _infoKategorie + "\n"
                  + " Found:\n" + _existingKategorie);
        }
        final HashMap<String, TableInfo.Column> _columnsPrzepisy = new HashMap<String, TableInfo.Column>(7);
        _columnsPrzepisy.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrzepisy.put("kategoria_id", new TableInfo.Column("kategoria_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrzepisy.put("nazwa", new TableInfo.Column("nazwa", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrzepisy.put("czas_przygotowania", new TableInfo.Column("czas_przygotowania", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrzepisy.put("instrukcje", new TableInfo.Column("instrukcje", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrzepisy.put("obrazek_url", new TableInfo.Column("obrazek_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrzepisy.put("wideo_url", new TableInfo.Column("wideo_url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPrzepisy = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPrzepisy = new HashSet<TableInfo.Index>(1);
        _indicesPrzepisy.add(new TableInfo.Index("index_Przepisy_kategoria_id", false, Arrays.asList("kategoria_id"), Arrays.asList("ASC")));
        final TableInfo _infoPrzepisy = new TableInfo("Przepisy", _columnsPrzepisy, _foreignKeysPrzepisy, _indicesPrzepisy);
        final TableInfo _existingPrzepisy = TableInfo.read(db, "Przepisy");
        if (!_infoPrzepisy.equals(_existingPrzepisy)) {
          return new RoomOpenHelper.ValidationResult(false, "Przepisy(com.smartcookbook.data.model.Recipe).\n"
                  + " Expected:\n" + _infoPrzepisy + "\n"
                  + " Found:\n" + _existingPrzepisy);
        }
        final HashMap<String, TableInfo.Column> _columnsSkladniki = new HashMap<String, TableInfo.Column>(4);
        _columnsSkladniki.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkladniki.put("przepis_id", new TableInfo.Column("przepis_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkladniki.put("nazwa", new TableInfo.Column("nazwa", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkladniki.put("ilosc", new TableInfo.Column("ilosc", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSkladniki = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSkladniki = new HashSet<TableInfo.Index>(1);
        _indicesSkladniki.add(new TableInfo.Index("index_Skladniki_przepis_id", false, Arrays.asList("przepis_id"), Arrays.asList("ASC")));
        final TableInfo _infoSkladniki = new TableInfo("Skladniki", _columnsSkladniki, _foreignKeysSkladniki, _indicesSkladniki);
        final TableInfo _existingSkladniki = TableInfo.read(db, "Skladniki");
        if (!_infoSkladniki.equals(_existingSkladniki)) {
          return new RoomOpenHelper.ValidationResult(false, "Skladniki(com.smartcookbook.data.model.Ingredient).\n"
                  + " Expected:\n" + _infoSkladniki + "\n"
                  + " Found:\n" + _existingSkladniki);
        }
        final HashMap<String, TableInfo.Column> _columnsUlubione = new HashMap<String, TableInfo.Column>(3);
        _columnsUlubione.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUlubione.put("user_uid", new TableInfo.Column("user_uid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUlubione.put("przepis_id", new TableInfo.Column("przepis_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUlubione = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUlubione = new HashSet<TableInfo.Index>(1);
        _indicesUlubione.add(new TableInfo.Index("index_Ulubione_przepis_id", false, Arrays.asList("przepis_id"), Arrays.asList("ASC")));
        final TableInfo _infoUlubione = new TableInfo("Ulubione", _columnsUlubione, _foreignKeysUlubione, _indicesUlubione);
        final TableInfo _existingUlubione = TableInfo.read(db, "Ulubione");
        if (!_infoUlubione.equals(_existingUlubione)) {
          return new RoomOpenHelper.ValidationResult(false, "Ulubione(com.smartcookbook.data.local.FavoriteEntity).\n"
                  + " Expected:\n" + _infoUlubione + "\n"
                  + " Found:\n" + _existingUlubione);
        }
        final HashMap<String, TableInfo.Column> _columnsListaZakupow = new HashMap<String, TableInfo.Column>(4);
        _columnsListaZakupow.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListaZakupow.put("user_uid", new TableInfo.Column("user_uid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListaZakupow.put("nazwa_produktu", new TableInfo.Column("nazwa_produktu", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListaZakupow.put("czy_kupione", new TableInfo.Column("czy_kupione", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListaZakupow = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListaZakupow = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoListaZakupow = new TableInfo("Lista_Zakupow", _columnsListaZakupow, _foreignKeysListaZakupow, _indicesListaZakupow);
        final TableInfo _existingListaZakupow = TableInfo.read(db, "Lista_Zakupow");
        if (!_infoListaZakupow.equals(_existingListaZakupow)) {
          return new RoomOpenHelper.ValidationResult(false, "Lista_Zakupow(com.smartcookbook.data.local.ShoppingItemEntity).\n"
                  + " Expected:\n" + _infoListaZakupow + "\n"
                  + " Found:\n" + _existingListaZakupow);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3250803e76e9b564c7a0bcfda974d421", "427e361f1aa991d4dc42e078beb61bee");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Kategorie","Przepisy","Skladniki","Ulubione","Lista_Zakupow");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Kategorie`");
      _db.execSQL("DELETE FROM `Przepisy`");
      _db.execSQL("DELETE FROM `Skladniki`");
      _db.execSQL("DELETE FROM `Ulubione`");
      _db.execSQL("DELETE FROM `Lista_Zakupow`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FavoriteDao.class, FavoriteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ShoppingItemDao.class, ShoppingItemDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public FavoriteDao favoriteDao() {
    if (_favoriteDao != null) {
      return _favoriteDao;
    } else {
      synchronized(this) {
        if(_favoriteDao == null) {
          _favoriteDao = new FavoriteDao_Impl(this);
        }
        return _favoriteDao;
      }
    }
  }

  @Override
  public ShoppingItemDao shoppingItemDao() {
    if (_shoppingItemDao != null) {
      return _shoppingItemDao;
    } else {
      synchronized(this) {
        if(_shoppingItemDao == null) {
          _shoppingItemDao = new ShoppingItemDao_Impl(this);
        }
        return _shoppingItemDao;
      }
    }
  }
}
