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
 * https://res.cloudinary.com/lux-group/image/upload/f_auto,fl_progressive,q_auto:eco,c_fill,g_auto,w_1920,ar_16:9/sxc8sdjm44qa9muxgrko
 *
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
public class ImageLoader implements BaseImageLoader {

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
        // notebyweiyi: should check onLoadedAction null otherwise will goto onError callback
        if (onLoadedAction != null) {
            Picasso.with(view.getContext()).load(url).placeholder(placeHolder).into(view, new Callback() {
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
        } else {
            Picasso.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
        }
    }

    @Override
    public void loadImageIntoTarget(View view, String url, Drawable placeHolder) {
        loadImageIntoTarget(view, url, placeHolder, null);
    }

    @Override
    public void loadImageIntoTarget(View view, String url, Drawable placeHolder, Consumer<Bitmap> onBitmapLoaded) {
        if (Strings.isNullOrEmpty(url)) {
            url = null;
        }
        Picasso.with(view.getContext()).load(url).placeholder(placeHolder).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (onBitmapLoaded != null) {
                    try {
                        onBitmapLoaded.accept(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    /** notebyweiyi: Some special image has its own method to set drawable, such as
                      {@link SubsamplingScaleImageView.setImage} */
                    view.setBackground(new BitmapDrawable(view.getContext().getResources(), bitmap));
                }
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
