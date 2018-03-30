package me.li2.android.wipro_assessment.data.source.remote;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.li2.android.wipro_assessment.data.model.CountryIntroEntry;

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

public class CountryDeserializer implements JsonDeserializer<List<CountryIntroEntry>> {
    @Override
    public List<CountryIntroEntry> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        String title;
        title = jsonObject.get("title").getAsString();

        Type type = new TypeToken<ArrayList<CountryIntroEntry>>() {}.getType();
        List<CountryIntroEntry> intros = context.deserialize(jsonObject.get("rows"), type);

        // remove all null elements
        intros.removeAll(Collections.singleton(null));

        return intros;
    }
}
