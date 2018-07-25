package arch;

import java.io.IOException;

import javax.inject.Inject;

import me.li2.android.architecture.utils.NetworkUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Detect offline error in Retrofit 2, by creating a class that implements Interceptor so that you can
 * perform a network connectivity check before executing the request.
 *
 * http://www.migapro.com/detect-offline-error-in-retrofit-2/
 * https://stablekernel.com/seamless-network-state-monitoring-with-retrofit-okhttp/
 *
 * Created by weiyi on 18/2/18.
 */
public class NetworkConnectivityInterceptor implements Interceptor {

    @Inject
    public NetworkConnectivityInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtils.isConnected()) {
            throw new NoNetworkException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
