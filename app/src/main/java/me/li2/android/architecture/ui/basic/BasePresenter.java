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

package me.li2.android.architecture.ui.basic;

public interface BasePresenter {

    /**
     * Start presenter business when its related view is ready.
     * <p>
     * Presenter doesn't make sense without a view, and only has a 1-to-1 relation with the view.
     * presenter maybe need to do some initialization stuff when the view is ready.
     * <p>
     * That's the reason we define this method which should be invoked when the view is ready, for example,
     * when Fragment.onViewCreated, Activity.onResume.
     */
    void start();
}
