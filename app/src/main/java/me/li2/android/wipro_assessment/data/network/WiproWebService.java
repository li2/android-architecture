package me.li2.android.wipro_assessment.data.network;

import android.arch.lifecycle.LiveData;

import me.li2.android.wipro_assessment.data.database.CountryEntry;
import retrofit2.http.GET;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */

public interface WiproWebService {
    @GET("facts.json")
    LiveData<ApiResponse<CountryEntry>> getCountry();
}
