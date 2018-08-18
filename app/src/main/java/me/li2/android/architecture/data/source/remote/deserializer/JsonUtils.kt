package me.li2.android.architecture.data.source.remote.deserializer

import com.google.gson.JsonObject

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 */
class JsonUtils {

    fun getJsonMemberAsString(jsonObject: JsonObject, memberName: String): String? {
        val element = jsonObject?.get(memberName)
        if (!element!!.isJsonNull) { // if (element != null && !element.isJsonNull)
            return element.asString
        }
        println("Error parsing $jsonObject null Json element $memberName")
        return null
    }

    fun getJsonMemberAsInt(jsonObject: JsonObject, memberName: String): Int? {
        val element = jsonObject?.get(memberName)
        if (!element!!.isJsonNull) { // if (element != null && !element.isJsonNull)
            return element.asInt
        }
        println("Error parsing $jsonObject null Json element $memberName")
        return null
    }
}
