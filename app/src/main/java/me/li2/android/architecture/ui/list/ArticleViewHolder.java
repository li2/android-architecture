package me.li2.android.architecture.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private View mItemView;

    @BindView(R.id.article_title_view)
    TextView mTitleView;

    @BindView(R.id.article_description_view)
    TextView mDescriptionView;

    @BindView(R.id.article_image_view)
    ImageView mImageView;
    
    // @Inject TODO why is null, the workaround is to inject by instructor
    ArticlesContract.Presenter mPresenter;

    public ArticleViewHolder(View itemView, ArticlesContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mItemView = itemView;
        mPresenter = presenter;
    }

    public void bindArticle(Article article) {
        if (article == null) {
            return;
        }
        mTitleView.setText(article.getTitle());
        mDescriptionView.setText(article.getDescription());
        loadImage(mImageView, article.getImageHref());
        mItemView.setOnClickListener(v -> mPresenter.onUserSelectArticle(article));
    }

    /**
     * Load image.
     *
     * Step1: Forces the request through the disk cache only, skipping network.
     * the reason to force cache firstly, because there is a issue that cached image cannot load succeed when offline.
     * Step2: then try online if cache failed.
     * https://stackoverflow.com/a/30686992/2722270
     * @param imageView
     * @param imageHref
     */
    private void loadImage(final ImageView imageView, final String imageHref) {
        imageView.setVisibility(View.GONE);

        Picasso.with(mContext)
                .load(imageHref)
                .networkPolicy(NetworkPolicy.OFFLINE) // force offline
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        Picasso.with(mContext)
                                .load(imageHref)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        imageView.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        imageView.setVisibility(View.GONE);
                                    }
                                });
                    }
                });
    }
}
