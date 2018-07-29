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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.google.common.base.Strings;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.inject.Inject;

import me.li2.android.architecture.R;

/**
 * Concrete implementation of the {@link BaseResourceProvider} interface.
 */
public class ResourceProvider implements BaseResourceProvider {

    @NonNull
    private final Context mContext;

    @Inject
    public ResourceProvider(@NonNull Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public String getString(@StringRes final int id) {
        return mContext.getString(id);
    }

    @NonNull
    @Override
    public String getString(@StringRes final int id, final Object... formatArgs) {
        return mContext.getString(id, formatArgs);
    }

    @Override
    public float getDimension(int id) {
        return mContext.getResources().getDimension(id);
    }


    public String integerToStringWithCommas(long number) {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
    }

    /**
     * @return $299/room
     */
    public String minPackagePrice(String currencyCode, int min, boolean isHotel) {
        return String.format(
                Locale.getDefault(), getString(R.string.min_price_format),
                Currency.getInstance(currencyCode).getSymbol(),
                integerToStringWithCommas(min),
                getString(isHotel ? R.string.price_unit_room : R.string.price_unit_pers));
    }

    /**
     * @return Valued up to $714
     */
    public String maxPackagePrice(String currencyCode, int max) {
        return String.format(
                getString(R.string.max_price_format),
                Currency.getInstance(currencyCode).getSymbol(),
                integerToStringWithCommas(max));
    }

    // TODO remove
    /**
     * @return Christchurch, New Zealand
     */
    public String offerLocation(String locationHeading, String locationSubheading) {
        return !Strings.isNullOrEmpty(locationSubheading)
                ? String.format(getString(R.string.offer_location_format), locationHeading, locationSubheading)
                : locationHeading;
    }

    /**
     * @return OFFER ENDS IN 2 DAYS
     */
    public String offerEndsInDays(long days) {
        return String.format(getString(R.string.offer_ends_in_days), days);
    }

    /**
     * @return 5-7 Nights
     */
    public String nightsRange(int from, int to) {
        return String.format(getString(R.string.nights_range_format, from, to));
    }
}
