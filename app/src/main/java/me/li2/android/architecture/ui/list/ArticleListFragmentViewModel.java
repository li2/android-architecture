package me.li2.android.architecture.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import architecture_components.utils.Resource;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.repository.DemoRepository;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class ArticleListFragmentViewModel extends ViewModel {

    private DemoRepository mRepository;

    public ArticleListFragmentViewModel(DemoRepository repository) {
        mRepository = repository;
    }

    public LiveData<Resource<List<Article>>> getArticleList() {
        return mRepository.loadArticleList();
    }
}
