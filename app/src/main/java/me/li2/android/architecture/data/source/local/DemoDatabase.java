package me.li2.android.architecture.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import me.li2.android.architecture.data.model.Article;

/**
 * Create a singleton Database
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

@Database(entities = {Article.class}, version = 1)
public abstract class DemoDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "demo_database";

    // For singleton
    private static final Object LOCK = new Object();
    private static volatile DemoDatabase sInstance;

    public abstract ArticleDao articleDao();

    public static DemoDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            DemoDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
