package me.li2.android.wipro_assessment.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.wipro_assessment.R;
import me.li2.android.wipro_assessment.data.model.Article;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String LOG_TAG = ArticleViewHolder.class.getSimpleName();

    private Context mContext;
    private Article mArticle;

    @BindView(R.id.article_title_view)
    TextView mTitleView;

    @BindView(R.id.article_description_view)
    TextView mDescriptionView;

    @BindView(R.id.article_image_view)
    ImageView mImageView;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mContext = itemView.getContext();
    }

    public void bindArticle(Article article) {
        if (article == null) {
            return;
        }
        mArticle = article;
        mTitleView.setText(article.getTitle());
        mDescriptionView.setText(article.getDescription());
        loadImage(mImageView, article.getImageHref());
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

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), mArticle.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
    }
}
