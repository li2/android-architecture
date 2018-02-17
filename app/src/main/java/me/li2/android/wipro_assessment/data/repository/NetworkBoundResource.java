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

package me.li2.android.wipro_assessment.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import me.li2.android.wipro_assessment.data.network.ApiResponse;
import me.li2.android.wipro_assessment.data.network.Resource;
import me.li2.android.wipro_assessment.utils.AppExecutors;
import me.li2.android.wipro_assessment.utils.Objects;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType> Type for the Resource data (normally represents db)
 * @param <RequestType> Type for the web service API response
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            // noinspection ConstantConditions

            /*
            notebyweiyi: how can i get status code on failure in retrofit 2? https://github.com/square/retrofit/issues/1612
            Callback.onResponse must check isSuccessful() (or the status code) to determine if the response was successful or not.
            Callback.onFailure invoked when a network exception occurred without response. t instance of Throwable
             */

            if (response.isSuccessful()) { // handle response, same as Callback.onResponse
                saveResultAndReInit(response);
            } else { // handle error, same as Callback.onFailure, wrapped by LiveDataCallAdapter
                onFetchFailed();
                result.addSource(dbSource, newData -> setValue(
                        Resource.error(newData, response.errorMessage, response.code, response.throwable)));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(ApiResponse<RequestType> response) {
        appExecutors.diskIO().execute(() -> {
            saveCallResult(processResponse(response));
            appExecutors.mainThread().execute(() ->
                    // we specially request a new live data,
                    // otherwise we will get immediately last cached value,
                    // which may not be updated with latest results received from network.
                    result.addSource(loadFromDb(),
                            newData -> setValue(Resource.success(newData)))
            );
        });
    }

    /**
     * Called when the fetch fails. The child class may want to reset components like rate limiter.
     */
    protected void onFetchFailed() {
    }

    /**
     * returns a LiveData that represents the resource, implemented in the base class.
     * @return
     */
    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    /**
     * Called to save the result of the API response into the database
     * @param item
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    /**
     * Called with the data in the database to decide whether it should be fetched from the network.
     * @param data
     * @return
     */
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    /**
     * Called to get the cached data from the database
     * @return
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    /**
     * Called to create the web service API call.
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
