package me.li2.android.architecture.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import javax.inject.Singleton;

import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.source.local.converter.BannerImageListConverter;

/**
 * Create a singleton Database
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

@Singleton
@Database(entities = {Article.class, Offer.class}, version = 1)
@TypeConverters({BannerImageListConverter.class})

public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "app_database";

    public abstract ArticlesDao articlesDao();

    public abstract OffersDao offersDao();
}
