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

package arch;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Common class used by API responses.
 * @param <T>
 *
 * https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.java
 */
public class ApiResponse<T> {

    /**
     * HTTP Status Codes
     * <ul>
     *     <li>200: The operation succeeded.
     *
     *     <li>304: Used with caching to indicate that the cached copy is still valid.
     *
     *     <li>400: The request is believed to be invalid in some way. The response body will contain an error message.
     *      You should display the error message to the user.
     *
     *     <li>401: An OAuth authentication failure occurred. You should ask the user to log in again.
     *
     *     <li>429: Your rate limit has been exceeded. Your rate limit will reset at the start of the next hour.
     *      You should not attempt to make any more calls until then.
     *
     *     <li>500: The server encountered an unexpected condition which prevented it from fulfilling the request.
     *      You should display a generic “whoops” error message to the user.
     *     <li>
     * </ul>
     * https://www.restapitutorial.com/httpstatuscodes.html
     */
    public final int code;

    @Nullable
    public final T body;

    @Nullable
    public final String errorMessage;

    /**
     * {@link retrofit2.Callback#onFailure(Call, Throwable)}} invoked when a network exception occurred without response,
     * for example, by creating a custom Exception class {@link NoNetworkException} allows us
     * to catch it for error handling. notebyweiyi
     */
    public final Throwable throwable;

    public ApiResponse(Throwable error) {
        throwable = error;
        code = HttpURLConnection.HTTP_INTERNAL_ERROR;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        throwable = null;
        code = response.code();
        if(response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Timber.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= HttpURLConnection.HTTP_OK && code < HttpURLConnection.HTTP_MULT_CHOICE;
    }
}
