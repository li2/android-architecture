package me.li2.android.wipro_assessment.data.network;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import me.li2.android.wipro_assessment.data.database.CountryIntroEntry;

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

public class CountryIntroDeserializer implements JsonDeserializer {
    @Override
    public CountryIntroEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String title = null, description = null, imageHref = null;

        final JsonObject jsonObject = json.getAsJsonObject();
        JsonElement element;

        if (!jsonObject.isJsonNull()) {
            element = jsonObject.get("title");
            if (!element.isJsonNull()) {
                title = element.getAsString();
            }

            element = jsonObject.get("description");
            if (!element.isJsonNull()) {
                description = element.getAsString();
            }

            element = jsonObject.get("imageHref");
            if (!element.isJsonNull()) {
                imageHref = element.getAsString();
            }
        }

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && TextUtils.isEmpty(imageHref)) {
            return null;
        }

        return new CountryIntroEntry(title, description, imageHref);
    }
}
