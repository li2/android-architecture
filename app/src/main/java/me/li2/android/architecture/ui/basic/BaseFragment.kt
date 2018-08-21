package me.li2.android.architecture.ui.basic

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import me.li2.android.architecture.utils.BaseResourceProvider
import javax.inject.Inject


/**
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var mResourceProvider: BaseResourceProvider

    // TODO annotation @LayoutRes?
    abstract val layout: Int

    @Nullable
    override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }
}
