package me.li2.android.architecture.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import me.li2.android.architecture.data.model.Article;

/**
 * Database access object.
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */
@Dao // Required annotation for Dao to be recognized by Room
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(Article... articles);

    @Query("SELECT * FROM articleTable")
    LiveData<List<Article>> getArticleList();

    @Query("SELECT * FROM articleTable WHERE id = :id")
    LiveData<Article> getArticle(int id);
}
