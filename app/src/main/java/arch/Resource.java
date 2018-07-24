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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.HttpURLConnection;

/**
 * A generic class that holds a value with its loading status. The value is from {@link ApiResponse}.
 * @param <T>
 *
 * https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.java
 */
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String errorMessage;

    public final int code;

    public final Throwable throwable;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String errorMessage, int code, Throwable throwable) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
        this.code = code;
        this.throwable = throwable;
    }

    // TODO how to distinguish data is cached or fetched from network?
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null, HttpURLConnection.HTTP_OK, null);
    }

    // add two more fields to let client know the error code / throwable (especially for custom Throwable). notebyweiyi
    public static <T> Resource<T> error(@Nullable T data, String errorMessage, int code, Throwable throwable) {
        return new Resource<>(Status.ERROR, data, errorMessage, code, throwable);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null, HttpURLConnection.HTTP_OK, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (errorMessage != null ? !errorMessage.equals(resource.errorMessage) : resource.errorMessage != null) {
            return false;
        }
        if (code != resource.code) {
            return false;
        }
        if (throwable != null ? !throwable.equals(resource.throwable) : resource.throwable != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + code;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (throwable != null ? throwable.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", code=" + code +
                ", errorMessage='" + errorMessage + '\'' +
                ", throwable=" + throwable  +
                ", data=" + data +
                '}';
    }
}
