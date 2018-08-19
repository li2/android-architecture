package me.li2.android.architecture.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 *
 * title : Beavers
 * description : Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony
 * imageHref : http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg
 *
 * Sample Json: https://li2.gitbooks.io/android-programming-journey/content/assets/demo-articles.json
 */

// TODO what's the purpose of indices?
@Entity(tableName = "ArticleTable", indices = [Index(value = *arrayOf("title"), unique = true)])
data class Article (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("imageHref")
    val imageHref: String?
) {
    // TODO this constructor is only for ArticleDeserializer, might be able to remove it when refactor the deserializer to kotlin
    @Ignore
    constructor(title: String?, description: String?, imageHref: String?) : this(0, title, description, imageHref)
}
