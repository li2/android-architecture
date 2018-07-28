package me.li2.android.architecture.data.source.remote.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.li2.android.architecture.data.model.Offer;

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

public class OfferArrayDeserializer implements JsonDeserializer<List<Offer>> {

    @Inject
    public OfferArrayDeserializer() {
    }

    @Override
    public List<Offer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        Type type = new TypeToken<ArrayList<Offer>>() {}.getType();
        List<Offer> offerList = context.deserialize(jsonObject.get("offers"), type);

        /** notebyweiyi: no need to define {@link OfferDeserializer} because Gson will do it automatically,
         define it when we need to customize the parse process, for example, to convert string to Date,
         to save the non-primary data type in the SQL Database we need to define these {@link android.arch.persistence.room.TypeConverter} */

        // remove all null elements
        //offerList.removeAll(Collections.singleton(null));

        return offerList;
    }
}
