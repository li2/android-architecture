package me.li2.android.architecture.data.source.remote;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 */
class JsonUtils {

    public static String getJsonMemberAsString(final JsonObject jsonObject, final String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if (!element.isJsonNull()) {
            return element.getAsString();
        }
        return null;
    }

    public static int getJsonMemberAsInt(final JsonObject jsonObject, final String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if (!element.isJsonNull()) {
            return element.getAsInt();
        }
        return 0;
    }
}
