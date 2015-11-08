package com.itangqi.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * @author 梁铖城
 * @date 2015年8月26日21:48:42
 * @description dao的管理层
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /*创建一个数据库文件*/
    public static void createAllTables(SQLiteDatabase db, boolean ifNoexits) {
        NoteDao.createTable(db, ifNoexits);
    }

    /*删除一个数据库*/
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        NoteDao.dropTable(db, ifExists);
    }

    @Override
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.None.Session, daoConfigMap);
    }

    @Override
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /*构造函数*/
    public static abstract class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            createAllTables(sqLiteDatabase, false);
        }
    }

    public static class DevOpenHelper extends OpenHelper {

        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    /*构造函数注册一个dao的观察类*/
    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(NoteDao.class);
    }


}
