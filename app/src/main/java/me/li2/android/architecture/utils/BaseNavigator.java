/*
 * Copyright 2016, The Android Open Source Project
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
package me.li2.android.architecture.utils;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Handles navigation between Activities in the app.
 */
public interface BaseNavigator {

    /**
     * Finish an Activity
     */
    void finishActivity();

    /**
     * Finish an Activity with a result.
     *
     * @param resultCode the result code to be set when finishing the Activity.
     */
    void finishActivityWithResult(int resultCode);

    /**
     * Start a new Activity for a result.
     *
     * @param cls         the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     */
    void startActivityForResult(Class cls, int requestCode);

    /**
     * Start a new Activity for a result with an extra
     *
     * @param cls        the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     * @param extraKey   the key for the extra that is passed in the Intent.
     * @param extraValue the value for the extra that is passed in the Intent.
     */
    void startActivityForResultWithExtra(Class cls, int requestCode, String extraKey, String extraValue);

    void startActivityForResultWithExtra(Class cls, int requestCode, String extraKey, int extraValue);

    /**
     * Start a new Activity for a result with an extras
     *
     * @param cls        the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     * @param extras the Bundle of extras that is passed in the Intent.
     */
    void startActivityForResultWithExtra(Class cls, int requestCode, Bundle extras);

    void addFragment(@NonNull Fragment fragment, @IdRes int containerId);

    void addFragment(@NonNull Fragment fragment, @IdRes int containerId, @NonNull View sharedElement);
}
