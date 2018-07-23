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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * Implementation of the {@link BaseNavigator}.
 */
public final class Navigator implements BaseNavigator {

    private final WeakReference<AppCompatActivity> mActivity;

    @Inject
    public Navigator(AppCompatActivity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void finishActivity() {
        if (mActivity.get() != null) {
            mActivity.get().finish();
        }
    }

    @Override
    public void finishActivityWithResult(int resultCode) {
        if (mActivity.get() != null) {
            mActivity.get().setResult(resultCode);
            mActivity.get().finish();
        }
    }

    @Override
    public void startActivityForResult(Class cls, int requestCode) {
        if (mActivity.get() != null) {
            Intent intent = new Intent(mActivity.get(), cls);
            mActivity.get().startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void startActivityForResultWithExtra(Class cls, int requestCode, String extraKey, String extraValue) {
        if (mActivity.get() != null) {
            Intent intent = new Intent(mActivity.get(), cls);
            intent.putExtra(extraKey, extraValue);
            mActivity.get().startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void startActivityForResultWithExtra(Class cls, int requestCode, Bundle extras) {
        if (mActivity.get() != null) {
            Intent intent = new Intent(mActivity.get(), cls);
            intent.putExtras(extras);
            mActivity.get().startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void addFragment(@NonNull Fragment fragment, @IdRes int containerId) {
        if (mActivity.get() != null) {
            FragmentManager fm = mActivity.get().getSupportFragmentManager();
            fm.beginTransaction()
                    .add(containerId, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        }
    }

    @Override
    public void addFragment(@NonNull Fragment fragment, @IdRes int containerId, @NonNull View sharedElement) {
        if (mActivity.get() != null) {
            // shared element transition between RecyclerView and Fragment
            // http://mikescamell.com/shared-element-transitions-part-4-recyclerview/
            // https://github.com/lgvalle/Material-Animations#shared-elements-between-fragments
            ChangeBounds changeBoundsTransition = new ChangeBounds();
            changeBoundsTransition.setDuration(mActivity.get().getApplicationContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
            fragment.setSharedElementEnterTransition(changeBoundsTransition);
            fragment.setAllowEnterTransitionOverlap(false);
            fragment.setAllowReturnTransitionOverlap(false);

            FragmentManager fm = mActivity.get().getSupportFragmentManager();
            fm.beginTransaction()
                    .addSharedElement(sharedElement, ViewCompat.getTransitionName(sharedElement))
                    .addToBackStack(fragment.toString())
                    .replace(containerId, fragment) // add not works for shared element transition
                    .commit();
        }
    }
}
