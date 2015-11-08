package com.itangqi.greendao;

import android.database.sqlite.SQLiteDatabase;


import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * @author  梁铖城
 * @date 2015年8月26日21:47:25
 * @description 关于dao的配置层
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig noteDaoConfig;

    private final  NoteDao noteDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        noteDaoConfig = daoConfigMap.get(NoteDao.class).clone();
        noteDaoConfig.initIdentityScope(type);

        noteDao = new NoteDao(noteDaoConfig, this);

        registerDao(Note.class, noteDao);
    }

    public void clear()
    {
        noteDaoConfig.getIdentityScope().clear();
    }
    public NoteDao getNoteDao(){return  noteDao;};
}
