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

@Entity(tableName = "ArticleTable", indices = arrayOf(Index(value = *arrayOf("title"), unique = true)))
class Article {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("title")
    var title: String? = null
        private set

    @SerializedName("description")
    var description: String? = null
        private set

    @SerializedName("imageHref")
    var imageHref: String? = null
        private set

    constructor(id: Int, title: String, description: String, imageHref: String) {
        this.id = id
        this.title = title
        this.description = description
        this.imageHref = imageHref
    }

    @Ignore
    constructor(title: String, description: String, imageHref: String) {
        this.title = title
        this.description = description
        this.imageHref = imageHref
    }

    override fun toString(): String {
        return "title: " + this.title + ", description: " + this.description + ", imageHref: " + this.imageHref
    }
}
