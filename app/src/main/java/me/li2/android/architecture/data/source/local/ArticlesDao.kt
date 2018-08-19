package me.li2.android.architecture.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.li2.android.architecture.data.model.Article

/**
 * Database access object.
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */
// Required annotation for Dao to be recognized by Room
@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(vararg articles: Article)

//    @get:Query("SELECT * FROM ArticleTable")
//    val articles: LiveData<List<Article>>
    @Query("SELECT * FROM ArticleTable")
    fun getArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM ArticleTable WHERE id = :id")
    fun getArticle(id: Int): LiveData<Article>
}
