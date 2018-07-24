package me.li2.android.architecture.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import io.reactivex.functions.Consumer;

/**
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
public interface BaseImageLoader {

    void loadImage(ImageView view, String url, Drawable placeHolder);

    void loadImage(ImageView view, String url, Drawable placeHolder, Consumer<Boolean> onLoadedAction);

    void loadImage(View view, String url, Drawable placeHolder);
}
