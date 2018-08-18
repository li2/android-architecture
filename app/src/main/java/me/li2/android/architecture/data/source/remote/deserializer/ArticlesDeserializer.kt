package me.li2.android.architecture.data.source.remote.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import me.li2.android.architecture.data.model.Article
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

class ArticlesDeserializer @Inject constructor() : JsonDeserializer<List<Article>> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<Article> {
        val jsonObject = json.asJsonObject

        val type = object : TypeToken<ArrayList<Article>>() {}.type
        val articleList = context.deserialize<List<Article?>>(jsonObject.get("rows"), type)

        // remove all null elements
        return articleList.filterNotNull()
    }
}
