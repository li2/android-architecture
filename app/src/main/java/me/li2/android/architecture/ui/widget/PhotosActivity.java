package me.li2.android.architecture.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import me.li2.android.architecture.R;

/**
 * Created by weiyi on 31/7/18.
 * https://github.com/li2
 */
public class PhotosActivity extends DaggerAppCompatActivity {

    public static final String EXTRA_PHOTOS_TITLE = "photos_title";
    public static final String EXTRA_PHOTO_INDEX = "photo_index";
    public static final String EXTRA_PHOTOS_CLOUDINARYIDS = "photos_cloudinary_ids";

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private String mTitle;
    private int mIndex;
    private ArrayList<String> mPhotoCloudinaryIds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_activity);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_PHOTOS_TITLE)) {
            mTitle = getIntent().getExtras().getString(EXTRA_PHOTOS_TITLE);
        }
        if (getIntent().hasExtra(EXTRA_PHOTO_INDEX)) {
            mIndex = getIntent().getExtras().getInt(EXTRA_PHOTO_INDEX);
        }
        if (getIntent().hasExtra(EXTRA_PHOTOS_CLOUDINARYIDS)) {
            mPhotoCloudinaryIds = getIntent().getExtras().getStringArrayList(EXTRA_PHOTOS_CLOUDINARYIDS);
        }

        setupViewPager();

        mViewPager.setCurrentItem(mIndex);
    }

    private void setupViewPager() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PhotoFragment.newInstance(mPhotoCloudinaryIds.get(position));
            }

            @Override
            public int getCount() {
                return mPhotoCloudinaryIds.size();
            }
        });
    }
}
