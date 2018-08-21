package me.li2.android.architecture.data.source.remote.deserializer

import android.text.TextUtils
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import me.li2.android.architecture.data.model.Article
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

class ArticleDeserializer @Inject constructor(): JsonDeserializer<Article> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Article? { // null cannot be a value of non-null type
        var title : String? = ""
        var description : String? = ""
        var imageHref: String? = null

        val jsonObject = json.asJsonObject
        if (!jsonObject.isJsonNull) {
            title = getJsonMemberAsString(jsonObject, "title")
            description = getJsonMemberAsString(jsonObject, "description")
            imageHref = getJsonMemberAsString(jsonObject, "imageHref")
            if (TextUtils.isEmpty(imageHref)) {
                // Picasso.load path must not be empty notebyweiyi
                imageHref = null
            }
        }

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && TextUtils.isEmpty(imageHref)) {
            return null
        }

        return Article(title = title, description = description, imageHref = imageHref)
    }
}
