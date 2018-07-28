package me.li2.android.architecture.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import javax.inject.Singleton;

import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.source.local.converter.BannerImageConverter;
import me.li2.android.architecture.data.source.local.converter.BannerImageListConverter;
import me.li2.android.architecture.data.source.local.converter.PriceConverter;
import me.li2.android.architecture.data.source.local.converter.PricePackageConverter;

/**
 * Create a singleton Database
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

@Singleton

@Database(entities = {
        Offer.class
}, version = 1)

// notebyweiyi: TypeConverter is used to figure out how to save non-primary data type into database
@TypeConverters({
        BannerImageConverter.class,
        BannerImageListConverter.class,
        PricePackageConverter.class,
        PriceConverter.class
})

public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "app_database";

    public abstract OffersDao offersDao();
}
