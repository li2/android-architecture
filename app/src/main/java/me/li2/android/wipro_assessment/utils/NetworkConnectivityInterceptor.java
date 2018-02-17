package me.li2.android.wipro_assessment.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Detect offline error in Retrofit 2
 *
 * Refer
 * http://www.migapro.com/detect-offline-error-in-retrofit-2/
 * https://stablekernel.com/seamless-network-state-monitoring-with-retrofit-okhttp/
 *
 * Created by weiyi on 18/2/18.
 */

public class NetworkConnectivityInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtils.isConnected()) {
            throw new NoNetworkException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
