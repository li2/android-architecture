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

// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt
// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.java

package arch

import java.net.HttpURLConnection

/**
 * A generic class that holds a value with its loading status. The value is from [ApiResponse].
 * @param <T>
 */
data class Resource<T>(val status: Status, val data: T?, val code: Int, val errorMessage: String?, val throwable: Throwable?) {
    companion object {

        // TODO how to distinguish data is cached or fetched from network?
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, HttpURLConnection.HTTP_OK, null, null)
        }

        // add two more fields to let client know the error code / throwable (especially for custom Throwable). notebyweiyi
        fun <T> error(data: T?, errorMessage: String, code: Int, throwable: Throwable?): Resource<T> {
            return Resource(Status.ERROR, data, code, errorMessage, throwable)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, HttpURLConnection.HTTP_OK, null, null)
        }
    }
}
