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
import java.net.HttpURLConnection

/**
 * Common class used by API responses.
 * @param <T>
 */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, error.message ?: "unknown error", error)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == HttpURLConnection.HTTP_NO_CONTENT) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(response.code(), errorMsg ?: "unknown error", null)
            }
        }
    }

    /**
     * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
     */
    class ApiEmptyResponse<T> : ApiResponse<T>()

    data class ApiErrorResponse<T>(
            /** HTTP Status Codes, see https://www.restapitutorial.com/httpstatuscodes.html */
            val code: Int,

            val errorMessage: String,

            /**
             * [retrofit2.Callback.onFailure]} invoked when a network exception occurred without response,
             * for example, by creating a custom Exception class [NoNetworkException] allows us
             * to catch it for error handling. notebyweiyi
             */
            val throwable: Throwable?
    ) : ApiResponse<T>()

    data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
}
