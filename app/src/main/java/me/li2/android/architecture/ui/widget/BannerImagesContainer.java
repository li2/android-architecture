package me.li2.android.architecture.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.functions.Consumer;
import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.Config;
import me.li2.android.architecture.utils.BaseImageLoader;

/**
 * Created by weiyi on 31/7/18.
 * https://github.com/li2
 */
public class BannerImagesContainer extends FrameLayout {

    private List<String> mCloudinaryIds;

    private BaseImageLoader mImageLoader;

    private BannerImageAdapter mAdapter;

    private Consumer<Integer> mOnPhotoClick;

    public BannerImagesContainer(@NonNull Context context) {
        this(context, null, 0);
    }

    public BannerImagesContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerImagesContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCloudinaryIds = new ArrayList<>();
        initView();
    }

    /**
     * Set with a set of images cloud id
     * @param cloudinaryIds
     * @param imageLoader
     */
    public void setCloudinaryIds(@Nonnull List<String> cloudinaryIds, BaseImageLoader imageLoader) {
        mCloudinaryIds = cloudinaryIds;
        mImageLoader = imageLoader;
        mAdapter.notifyDataSetChanged();
    }

    public void setPhotoClickAction(Consumer<Integer> action) {
        mOnPhotoClick = action;
    }

    private void initView() {
        // notebyweiyi: set attacheToRoot to true otherwise the list will not show
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.banner_images_container_widget, this, true);
        RecyclerView recyclerView = rootView.findViewById(R.id.banner_images_list_view);
        IndefinitePagerIndicator indicator = rootView.findViewById(R.id.banner_images_indicator);
        indicator.attachToRecyclerView(recyclerView);
        setupRecyclerView(recyclerView);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setScrollContainer(false);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new BannerImageAdapter();
        recyclerView.setAdapter(mAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private class BannerImageAdapter extends RecyclerView.Adapter<BannerImageViewHolder> {

        @Override
        public BannerImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_viewholder, parent, false);
            return new BannerImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BannerImageViewHolder holder, int position) {
            holder.bindImageCloudinaryId(mCloudinaryIds.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mCloudinaryIds != null ? mCloudinaryIds.size() : 0;
        }
    }

    private class BannerImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private ProgressBar mProgressBar;

        public BannerImageViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mProgressBar = itemView.findViewById(R.id.progress_bar);
        }

        public void bindImageCloudinaryId(String cloudinaryId, int position) {
            mProgressBar.setVisibility(VISIBLE);
            mImageLoader.loadImage(mImageView, Config.photoUrl(cloudinaryId), null,
                    succeed -> mProgressBar.setVisibility(GONE));
            mImageView.setOnClickListener(view -> {
                if (mOnPhotoClick != null) {
                    try {
                        mOnPhotoClick.accept(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
