package me.li2.android.architecture.data.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.li2.android.architecture.data.model.PricePackage;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
public class PricePackageConverter extends BaseConverter<PricePackage>{
    @Override
    @TypeConverter
    public String toString(PricePackage pricePackage) {
        return new Gson().toJson(pricePackage);
    }

    @Override
    @TypeConverter
    public PricePackage fromString(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<PricePackage>(){}.getType());
    }
}
