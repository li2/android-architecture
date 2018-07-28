package me.li2.android.architecture.data.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.li2.android.architecture.data.model.BannerImage;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 */
public class BannerImageConverter extends BaseConverter<BannerImage> {
    @Override
    @TypeConverter
    public String toString(BannerImage bannerImage) {
        return new Gson().toJson(bannerImage);
    }

    @Override
    @TypeConverter
    public BannerImage fromString(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<BannerImage>() {}.getType());
    }
}
