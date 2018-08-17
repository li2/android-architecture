package me.li2.android.architecture.data.source.remote.deserializer;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 */
class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static String getJsonMemberAsString(final JsonObject jsonObject, final String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if (element != null && !element.isJsonNull()) {
            return element.getAsString();
        } else {
            Log.e(TAG, "Error parsing null Json element " + memberName);
        }
        return null;
    }

    public static int getJsonMemberAsInt(final JsonObject jsonObject, final String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if (element != null && !element.isJsonNull()) {
            return element.getAsInt();
        } else {
            Log.e(TAG, "Error parsing null Json element " + memberName);
        }
        return 0;
    }
}
