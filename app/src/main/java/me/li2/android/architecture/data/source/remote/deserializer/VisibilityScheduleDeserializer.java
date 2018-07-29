package me.li2.android.architecture.data.source.remote.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import javax.inject.Inject;

import me.li2.android.architecture.data.model.VisibilitySchedule;
import me.li2.android.architecture.utils.DateUtils;

/**
 * Created by weiyi on 29/7/18.
 * https://github.com/li2
 */
public class VisibilityScheduleDeserializer implements JsonDeserializer<VisibilitySchedule> {

    @Inject
    public VisibilityScheduleDeserializer() {

    }

    /*
    "visibilitySchedules": {
        "world": {
            "id": 3937,
            "offer_id_salesforce_external": "0060I00000UJ3cyQAD",
            "start": "2018-07-23T06:20:22.000Z",
            "end": "2018-07-30T07:20:00.000Z",
            "type": "list_visibility",
            "region": "world",
            "brand": "luxuryescapes"
        }
    }

    notebyweiyi: contains more than one object, workaround is to modify the demo json file to have same "world" !

    "visibilitySchedules": {
        "NZ": {
            ....
        },
        "AU": {
            ....
        }
    }
     */

    @Override
    public VisibilitySchedule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        /* notebyweiyi: if you don't want to define such "world" json element as a Java object,
        then we need to define this deserializer to parse json manually. */

        JsonObject rootJsonObject = json.getAsJsonObject();
        if (rootJsonObject != null) {
            JsonObject worldJsonObject = rootJsonObject.getAsJsonObject("world");
            if (worldJsonObject != null) {
                String start = JsonUtils.getJsonMemberAsString(worldJsonObject, "start");
                String end = JsonUtils.getJsonMemberAsString(worldJsonObject, "end");
                return new VisibilitySchedule(DateUtils.getUtcDate(start), DateUtils.getUtcDate(end));
            }
        }

        return null;
    }
}
