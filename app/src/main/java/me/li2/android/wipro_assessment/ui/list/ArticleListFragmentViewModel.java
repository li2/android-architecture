package me.li2.android.wipro_assessment.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import architecture_components.utils.Resource;
import me.li2.android.wipro_assessment.data.model.Article;
import me.li2.android.wipro_assessment.data.repository.WiproRepository;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class ArticleListFragmentViewModel extends ViewModel {

    private WiproRepository mRepository;

    public ArticleListFragmentViewModel(WiproRepository repository) {
        mRepository = repository;
    }

    public LiveData<Resource<List<Article>>> getArticleList() {
        return mRepository.loadArticleList();
    }
}
