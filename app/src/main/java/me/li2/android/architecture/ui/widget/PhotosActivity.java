package me.li2.android.architecture.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindView(R.id.toolbar_layout)
    AppBarLayout mToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitleView;

    @BindView(R.id.toolbar_icon)
    ImageView mToolbarIcon;

    private String mTitle;
    private int mIndex;
    private ArrayList<String> mPhotoCloudinaryIds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.photos_activity);

        if (getIntent().hasExtra(EXTRA_PHOTOS_TITLE)) {
            mTitle = getIntent().getExtras().getString(EXTRA_PHOTOS_TITLE);
        }
        if (getIntent().hasExtra(EXTRA_PHOTO_INDEX)) {
            mIndex = getIntent().getExtras().getInt(EXTRA_PHOTO_INDEX);
        }
        if (getIntent().hasExtra(EXTRA_PHOTOS_CLOUDINARYIDS)) {
            mPhotoCloudinaryIds = getIntent().getExtras().getStringArrayList(EXTRA_PHOTOS_CLOUDINARYIDS);
        }

        ButterKnife.bind(this);

        setupToolbar();

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

    private void setupToolbar() {
        mToolbarLayout.setBackground(null);
        // notebyweiyi: change app:elevation programmatically, not works for android:elevation
        ViewCompat.setElevation(mToolbarLayout, 0);
        mToolbarTitleView.setText(mTitle);
        mToolbarIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back));
        mToolbarIcon.setOnClickListener(view -> finish());
    }
}
