package arch

import me.li2.android.architecture.utils.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Detect offline error in Retrofit 2, by creating a class that implements Interceptor so that you can
 * perform a network connectivity check before executing the request.
 *
 * http://www.migapro.com/detect-offline-error-in-retrofit-2/
 * https://stablekernel.com/seamless-network-state-monitoring-with-retrofit-okhttp/
 *
 * Created by weiyi on 18/2/18.
 */
class NetworkConnectivityInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkConnected) {
            throw NoNetworkException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
