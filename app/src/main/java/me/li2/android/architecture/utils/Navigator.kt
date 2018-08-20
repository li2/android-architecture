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

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.view.View
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Implementation of the [BaseNavigator].
 */
class Navigator @Inject
constructor(activity: AppCompatActivity) : BaseNavigator {

    private val mActivity: WeakReference<AppCompatActivity> = WeakReference(activity)

    override fun finishActivity() {
        mActivity.get()?.finish()
    }

    override fun finishActivityWithResult(resultCode: Int) {
        mActivity.get()?.let {
            it.setResult(resultCode)
            it.finish()
        }
    }

    override fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        mActivity.get()?.let {
            val intent = Intent(it, cls)
            it.startActivityForResult(intent, requestCode)
        }
    }

    override fun startActivityForResultWithExtra(cls: Class<*>, requestCode: Int, extraKey: String, extraValue: String) {
        mActivity.get()?.let {
            val intent = Intent(it, cls)
            intent.putExtra(extraKey, extraValue)
            it.startActivityForResult(intent, requestCode)

        }
    }

    override fun startActivityForResultWithExtra(cls: Class<*>, requestCode: Int, extraKey: String, extraValue: Int) {
        mActivity.get()?.let {
            val intent = Intent(it, cls)
            intent.putExtra(extraKey, extraValue)
            it.startActivityForResult(intent, requestCode)
        }
    }

    override fun startActivityForResultWithExtra(cls: Class<*>, requestCode: Int, extras: Bundle) {
        mActivity.get()?.let {
            val intent = Intent(it, cls)
            intent.putExtras(extras)
            it.startActivityForResult(intent, requestCode)
        }
    }

    override fun addFragment(fragment: Fragment, @IdRes containerId: Int) {
        mActivity.get()?.let {
            it.supportFragmentManager
                    .beginTransaction()
                    .add(containerId, fragment)
                    .addToBackStack(fragment.toString())
                    .commit()
        }
    }

    override fun addFragment(fragment: Fragment, @IdRes containerId: Int, sharedElement: View) {
        mActivity.get()?.let {
            // shared element transition between RecyclerView and Fragment
            // http://mikescamell.com/shared-element-transitions-part-4-recyclerview/
            // https://github.com/lgvalle/Material-Animations#shared-elements-between-fragments
            val changeBoundsTransition = ChangeBounds()
            changeBoundsTransition.duration = it.applicationContext.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            fragment.sharedElementEnterTransition = changeBoundsTransition
            fragment.allowEnterTransitionOverlap = false
            fragment.allowReturnTransitionOverlap = false

            it.supportFragmentManager
                    .beginTransaction()
                    .addSharedElement(sharedElement, ViewCompat.getTransitionName(sharedElement))
                    .addToBackStack(fragment.toString())
                    .replace(containerId, fragment) // add not works for shared element transition
                    .commit()
        }
    }
}
