package me.li2.android.architecture.data.source.remote;

import android.arch.lifecycle.LiveData;

import java.util.List;

import arch.ApiResponse;
import me.li2.android.architecture.data.model.Offer;
import retrofit2.http.GET;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 */
public interface OffersServiceApi {
    @GET("demo-le-offers.json")
    LiveData<ApiResponse<List<Offer>>> getOffers();
}
