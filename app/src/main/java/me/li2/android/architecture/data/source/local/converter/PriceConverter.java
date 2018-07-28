package me.li2.android.architecture.data.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.li2.android.architecture.data.model.Price;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
public class PriceConverter extends BaseConverter<Price> {
    @Override
    @TypeConverter
    public String toString(Price price) {
        return new Gson().toJson(price);
    }

    @Override
    @TypeConverter
    public Price fromString(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<Price>(){}.getType());
    }
}
