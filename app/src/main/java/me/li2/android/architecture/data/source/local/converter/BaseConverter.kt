package me.li2.android.architecture.data.source.local.converter

import android.arch.persistence.room.TypeConverter

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
internal abstract class BaseConverter<T> {
    @TypeConverter
    abstract fun toString(t: T): String

    @TypeConverter
    abstract fun fromString(jsonString: String): T
}
