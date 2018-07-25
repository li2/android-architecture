package me.li2.android.architecture.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.google.common.base.Strings;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
public class ImageLoader implements BaseImageLoader {

    @Inject
    Picasso mPicasso;

    @Inject
    public ImageLoader() {
    }

    @Override
    public void loadImage(ImageView view, String url, Drawable placeHolder) {
        loadImage(view, url, placeHolder, null);
    }

    @Override
    public void loadImage(ImageView view, String url, Drawable placeHolder, Consumer<Boolean> onLoadedAction) {
        if (Strings.isNullOrEmpty(url)) {
            url = null;
        }
        mPicasso.with(view.getContext()).load(url).placeholder(placeHolder).into(view, new Callback() {
            @Override
            public void onSuccess() {
                try {
                    onLoadedAction.accept(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                try {
                    onLoadedAction.accept(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void loadImage(View view, String url, Drawable placeHolder) {
        if (Strings.isNullOrEmpty(url)) {
            url = null;
        }
        mPicasso.with(view.getContext()).load(url).placeholder(placeHolder).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                view.setBackground(new BitmapDrawable(view.getContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }
}
