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
package me.li2.android.architecture.utils

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View

/**
 * Handles navigation between Activities in the app.
 */
interface BaseNavigator {

    /**
     * Finish an Activity
     */
    fun finishActivity()

    /**
     * Finish an Activity with a result.
     *
     * @param resultCode the result code to be set when finishing the Activity.
     */
    fun finishActivityWithResult(resultCode: Int)

    /**
     * Start a new Activity for a result.
     *
     * @param cls         the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int)

    /**
     * Start a new Activity for a result with an extra
     *
     * @param cls        the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     * @param extraKey   the key for the extra that is passed in the Intent.
     * @param extraValue the value for the extra that is passed in the Intent.
     */
    fun startActivityForResultWithExtra(cls: Class<*>, requestCode: Int, extraKey: String, extraValue: String)

    fun startActivityForResultWithExtra(cls: Class<*>, requestCode: Int, extraKey: String, extraValue: Int)

    /**
     * Start a new Activity for a result with an extras
     *
     * @param cls        the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     * @param extras the Bundle of extras that is passed in the Intent.
     */
    fun startActivityForResultWithExtra(cls: Class<*>, requestCode: Int, extras: Bundle)

    fun addFragment(fragment: Fragment, @IdRes containerId: Int)

    fun addFragment(fragment: Fragment, @IdRes containerId: Int, sharedElement: View)
}
