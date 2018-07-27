package me.li2.android.architecture.data.source.remote;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import javax.inject.Inject;

import me.li2.android.architecture.data.model.Offer;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 */

public class OfferDeserializer implements JsonDeserializer {

    @Inject
    public OfferDeserializer() {
    }

    @Override
    public Offer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.isJsonNull()) {
            return null;
        }

        String bookingType = JsonUtils.getJsonMemberAsString(jsonObject, "bookingType");
        String type = JsonUtils.getJsonMemberAsString(jsonObject, "type");
        String name = JsonUtils.getJsonMemberAsString(jsonObject, "name");
        String location = JsonUtils.getJsonMemberAsString(jsonObject, "location");
        String description = JsonUtils.getJsonMemberAsString(jsonObject, "description");
        String highlights = JsonUtils.getJsonMemberAsString(jsonObject, "highlights");
        String facilities = JsonUtils.getJsonMemberAsString(jsonObject, "facilities");
        String finePrint = JsonUtils.getJsonMemberAsString(jsonObject, "finePrint");
        String gettingThere = JsonUtils.getJsonMemberAsString(jsonObject, "gettingThere");
        String bookingGuarantee = JsonUtils.getJsonMemberAsString(jsonObject, "bookingGuarantee");
        String runDate = JsonUtils.getJsonMemberAsString(jsonObject, "runDate");
        String endDate = JsonUtils.getJsonMemberAsString(jsonObject, "endDate");
        int maxNumNights = JsonUtils.getJsonMemberAsInt(jsonObject, "maxNumNights");
        int minNumNights = JsonUtils.getJsonMemberAsInt(jsonObject, "minNumNights");

        return new Offer(
                bookingType,
                type,
                name,
                location,
                description,
                highlights,
                facilities,
                finePrint,
                gettingThere,
                bookingGuarantee,
                runDate,
                endDate,
                maxNumNights,
                minNumNights
        );
    }
}
