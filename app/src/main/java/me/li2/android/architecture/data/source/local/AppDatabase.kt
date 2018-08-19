package me.li2.android.architecture.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import me.li2.android.architecture.data.model.Article
import javax.inject.Singleton

/**
 * Create a singleton Database
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

@Singleton
@Database(entities = [Article::class], version = 1)
// notebyweiyi: TypeConverter is used to figure out how to save non-primary data type into database
@TypeConverters
abstract class AppDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}
