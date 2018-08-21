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

import android.content.Context
import android.support.annotation.StringRes

import javax.inject.Inject

/**
 * Concrete implementation of the [BaseResourceProvider] interface.
 */
class ResourceProvider @Inject
constructor(private val mContext: Context) : BaseResourceProvider {

    override fun getString(@StringRes id: Int): String {
        return mContext.getString(id)
    }

    override fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return mContext.getString(id, *formatArgs)
    }

    override fun getDimension(id: Int): Float {
        return mContext.resources.getDimension(id)
    }
}
