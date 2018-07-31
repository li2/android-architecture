package me.li2.android.architecture.ui.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import javax.inject.Inject;

import butterknife.BindView;
import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.Config;
import me.li2.android.architecture.ui.basic.BaseFragment;
import me.li2.android.architecture.utils.BaseImageLoader;

/**
 * Created by weiyi on 31/7/18.
 * https://github.com/li2
 */
public class PhotoFragment extends BaseFragment {

    private static final String ARG_PHOTO_CLOUDINARYID = "photo_cloudinary_id";

    private String mPhotoCloudinaryId;

    @Inject
    BaseImageLoader mImageLoader;

    @BindView(R.id.scalable_image_view)
    SubsamplingScaleImageView mImageView;

    public PhotoFragment() {
    }

    public static PhotoFragment newInstance(String photoCloudinaryId) {
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_CLOUDINARYID, photoCloudinaryId);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhotoCloudinaryId = getArguments().getString(ARG_PHOTO_CLOUDINARYID);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.photo_fragment;
    }

    @Override
    public void setupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImageLoader.loadImageIntoTarget(mImageView, Config.photoUrl(mPhotoCloudinaryId), null,
                bitmap -> mImageView.setImage(ImageSource.bitmap(bitmap)));
    }
}
