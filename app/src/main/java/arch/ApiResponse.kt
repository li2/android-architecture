/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt
// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.java

package arch

import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Common class used by API responses.
 * @param <T>
 */
class ApiResponse<T> {

    /**
     * HTTP Status Codes
     * https://www.restapitutorial.com/httpstatuscodes.html
     */
    val code: Int

    val body: T?

    val errorMessage: String?

    /**
     * [retrofit2.Callback.onFailure]} invoked when a network exception occurred without response,
     * for example, by creating a custom Exception class [NoNetworkException] allows us
     * to catch it for error handling. notebyweiyi
     */
    val throwable: Throwable?

    val isSuccessful: Boolean
        get() = code >= HttpURLConnection.HTTP_OK && code < HttpURLConnection.HTTP_MULT_CHOICE

    constructor(error: Throwable) {
        throwable = error
        code = HttpURLConnection.HTTP_INTERNAL_ERROR
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        throwable = null
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Timber.e(ignored, "error while parsing response")
                }
            }
            if (message == null || message.trim().isEmpty()) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }

    // TODO ApiEmptyResponse ApiSuccessResponse ApiErrorResponse
}
