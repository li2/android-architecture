package me.li2.android.architecture.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

import io.reactivex.functions.Consumer

/**
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
interface BaseImageLoader {

    fun loadImage(view: ImageView, url: String?, placeHolder: Drawable?)

    fun loadImage(view: ImageView, url: String?, placeHolder: Drawable?, onLoadedAction: Consumer<Boolean>?)

    fun loadImage(view: View, url: String?, placeHolder: Drawable?)
}
