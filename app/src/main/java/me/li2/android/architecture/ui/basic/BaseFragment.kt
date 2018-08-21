package me.li2.android.architecture.ui.basic

import dagger.android.support.DaggerFragment
import me.li2.android.architecture.utils.BaseResourceProvider
import javax.inject.Inject

/**
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var mResourceProvider: BaseResourceProvider

    abstract val layout: Int
}
