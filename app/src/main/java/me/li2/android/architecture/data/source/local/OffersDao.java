package me.li2.android.architecture.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import me.li2.android.architecture.data.model.Offer;

/**
 * Database access object.
 *
 * Created by weiyi on 27/07/2018.
 * https://github.com/li2
 */
@Dao
public interface OffersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(Offer... offers);

    @Query("SELECT * FROM OfferTable")
    LiveData<List<Offer>> getOffers();

    @Query("SELECT * FROM OfferTable WHERE idSalesforceExternal = :id")
    LiveData<Offer> getOffer(String id);
}
