package me.li2.android.wipro_assessment.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Database access object.
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */
@Dao // Required annotation for Dao to be recognized by Room
public interface CountryIntroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(CountryIntroEntry... countryIntro);

    @Query("SELECT * FROM countryIntroTable")
    LiveData<List<CountryIntroEntry>> getCountryIntroList();
}
