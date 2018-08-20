package me.li2.android.architecture.ui.articledetail.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.li2.android.architecture.data.repository.ArticlesRepository
import javax.inject.Inject

/**
 * The constructor that is automatically called by ViewModelProvider is the default one -
 * it takes no arguments. If you want to create a different constructor for a view model,
 * you'll need to make a view model provider factory.
 *
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

class ArticleDetailViewModelFactory
@Inject
constructor() : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var mRepository: ArticlesRepository

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticleDetailViewModel(mRepository) as T
    }
}
