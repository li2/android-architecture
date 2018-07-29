package me.li2.android.architecture.data.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.li2.android.architecture.data.model.VisibilitySchedule;

/**
 * Created by weiyi on 29/7/18.
 * https://github.com/li2
 */
public class VisibilityScheduleConverter extends BaseConverter<VisibilitySchedule> {
    @Override
    @TypeConverter
    public String toString(VisibilitySchedule visibilitySchedule) {
        return new Gson().toJson(visibilitySchedule);
    }

    @Override
    @TypeConverter
    public VisibilitySchedule fromString(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<VisibilitySchedule>(){}.getType());
    }
}
