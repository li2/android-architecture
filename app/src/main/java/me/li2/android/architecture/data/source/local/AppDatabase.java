package me.li2.android.architecture.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import javax.inject.Singleton;

import me.li2.android.architecture.data.model.Article;

/**
 * Create a singleton Database
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

@Singleton

@Database(entities = {
        Article.class
}, version = 1)

// notebyweiyi: TypeConverter is used to figure out how to save non-primary data type into database
@TypeConverters({
})

public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "app_database";

    public abstract ArticlesDao articlesDao();
}
