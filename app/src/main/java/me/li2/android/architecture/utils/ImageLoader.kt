package me.li2.android.architecture.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
class ImageLoader @Inject
constructor() : BaseImageLoader {

    @Inject
    lateinit var mPicasso: Picasso

    override fun loadImage(view: ImageView, url: String?, placeHolder: Drawable?) {
        loadImage(view, url, placeHolder, null)
    }

    override fun loadImage(view: ImageView, url: String?, placeHolder: Drawable?, onLoadedAction: Consumer<Boolean>?) {
        var url = if (url.isNullOrEmpty()) null else url

        mPicasso.load(url).placeholder(placeHolder).into(view, object : Callback {
            override fun onSuccess() {
                onLoadedAction?.accept(true)
            }

            override fun onError() {
                onLoadedAction?.accept(false)
            }
        })
    }

    override fun loadImage(view: View, url: String?, placeHolder: Drawable?) {
        var url = if (url.isNullOrEmpty()) null else url

        mPicasso.load(url).placeholder(placeHolder).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                view.background = BitmapDrawable(view.context.resources, bitmap)
            }

            override fun onBitmapFailed(errorDrawable: Drawable) {}

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        })
    }
}
