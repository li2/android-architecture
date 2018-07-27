package me.li2.android.architecture.data.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import me.li2.android.architecture.data.model.BannerImage;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 */
public class BannerImageListConverter {
    @TypeConverter
    public static String imageToString(List<BannerImage> images) {
        return new Gson().toJson(images);
    }

    @TypeConverter
    public static List<BannerImage> fromString(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<List<BannerImage>>() {}.getType());
    }
}
