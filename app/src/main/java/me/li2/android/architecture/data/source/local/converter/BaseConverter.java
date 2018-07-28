package me.li2.android.architecture.data.source.local.converter;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
abstract class BaseConverter<T> {
    @TypeConverter
    public abstract String toString(T t);

    @TypeConverter
    public abstract T fromString(String jsonString);
}
