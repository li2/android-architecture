package me.li2.android.architecture.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiyi on 29/7/18.
 * https://github.com/li2
 */
public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    private static final String UTC_FULL_TIME_PATTERN ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Date getDateWithPattern(@NonNull String timestamp, @NonNull String pattern, @Nullable TimeZone timeZone) {
        Date result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            if (timeZone != null) {
                sdf.setTimeZone(timeZone);
            }
            result = sdf.parse(timestamp);
        } catch (ParseException | IllegalArgumentException e) {
            Log.e(TAG, "Error getDateWithPattern(" + timestamp + ")" + e.getMessage());
        }
        return result;
    }

    public static Date getUtcDate(@NonNull String timestamp) {
        return getDateWithPattern(timestamp, UTC_FULL_TIME_PATTERN, TimeZone.getTimeZone("UTC"));
    }

    public static long getDaysBetween(Date startDate, Date endDate) {
        final long diff = endDate.getTime() - startDate.getTime();
        if (diff <= 0) {
            return 0;
        }
        long days = diff / TimeUnit.DAYS.toMillis(1);
        return days;
    }
}
