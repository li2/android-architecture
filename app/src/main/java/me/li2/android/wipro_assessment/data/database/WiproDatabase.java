package me.li2.android.wipro_assessment.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Create a singleton Database
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

@Database(entities = {CountryIntroEntry.class}, version = 1)
public abstract class WiproDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "wipro_database";

    // For singleton
    private static final Object LOCK = new Object();
    private static volatile WiproDatabase sInstance;

    public abstract CountryIntroDao countryIntroDao();

    public static WiproDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WiproDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
