package com.itangqi.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * @author 梁铖城
 * @date 2015年8月26日21:46:31
 * @description 关于javabean的dao层
 */
public class NoteDao extends AbstractDao<Note, Long> {

    public static final String TABLENAME = "NOTE";

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Image = new Property(1, String.class, "image", false, "IMAGE");
        public final static Property Jiage = new Property(2, String.class, "jiage", false, "JIAGE");
        public final static Property Miaoshu = new Property(3, String.class, "miaoshu", false, "MIAOSHU");

        public final static Property Shangpinid = new Property(4, String.class, "shangpinid", false, "SHANGPINID");
        public final static Property Shijian = new Property(5, String.class, "shijian", false, "SHIJIAN");
        public final static Property Title = new Property(6, String.class, "title", false, "TITLE");
        public final static Property Dealid = new Property(7, String.class, "dealid", false, "DEALID");

        public final static Property Buyid = new Property(8, String.class, "buyid", false, "BUYID");
        public final static Property Zhuangtai = new Property(9, String.class, "zhuangtai", false, "ZHUANGTAI");

        public final static Property Lianxi = new Property(10, String.class, "lianxi", false, "LIANXI");
        public final static Property Qufu = new Property(11, java.util.Date.class, "qufu", false, "QUFU");
        public final static Property Jiaoyifangshi = new Property(12, String.class, "jiaoyifangshi", false, "JIAOYIFANGSHI");
    }

    ;


    public NoteDao(DaoConfig config) {
        super(config);
    }

    public NoteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'NOTE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'IMAGE' TEXT  ," + // 1: text
                "'JIAGE' TEXT  ," + // 1: text
                "'MIAOSHU' TEXT  ," + // 1: text
                "'SHANGPINID' TEXT  ," + // 1: text
                "'SHIJIAN' TEXT ," + // 1: text
                "'TITLE' TEXT  ," + // 1: text
                "'DEALID' TEXT  ," + // 1: text
                "'BUYID' TEXT  ," + // 1: text
                "'ZHUANGTAI' TEXT  ," + // 1: text
                "'LIANXI' TEXT," + // 2: comment
                "'QUFU' TEXT," + // 2: comment
                "'JIAOYIFANGSHI' TEXT);"); // 3: date
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'NOTE'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, Note entity) {
        //这里为什么是从1开始？难道是因为数据库里面有rowid
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getImage());
        stmt.bindString(3, entity.getJiage());
        stmt.bindString(4, entity.getMiaoshu());
        stmt.bindString(5, entity.getShangpinid());
        stmt.bindString(6, entity.getShijian());
        stmt.bindString(7, entity.getTitle());
        stmt.bindString(8, entity.getDealid());
        stmt.bindString(9, entity.getBuyid());
        stmt.bindString(10, entity.getZhuangtai());
        stmt.bindString(11, entity.getLianxi());
        stmt.bindString(12, entity.getQufu());
        stmt.bindString(13, entity.getJiaoyifangshi());
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Note readEntity(Cursor cursor, int offset) {
        Note entity = new Note(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1),
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2),
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3),
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4),
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5),
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6),
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7),
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8),
                cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9),
                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10),
                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11),
                cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12)
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, Note entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImage(cursor.getString(offset + 1));
        entity.setJiage(cursor.getString(offset + 2));
        entity.setMiaoshu(cursor.getString(offset + 3));
        entity.setShangpinid(cursor.getString(offset + 4));
        entity.setShijian(cursor.getString(offset + 5));
        entity.setTitle(cursor.getString(offset + 6));
        entity.setDealid(cursor.getString(offset + 7));
        entity.setBuyid(cursor.getString(offset + 8));
        entity.setZhuangtai(cursor.getString(offset + 9));
        entity.setLianxi(cursor.getString(offset + 10));
        entity.setQufu(cursor.getString(offset + 11));
        entity.setJiaoyifangshi(cursor.getString(offset + 12));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(Note entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(Note entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
