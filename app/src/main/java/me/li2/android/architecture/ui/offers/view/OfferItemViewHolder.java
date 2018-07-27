package me.li2.android.architecture.ui.offers.view;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.ui.offers.viewmodel.OfferItem;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.ViewUtils;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */
public class OfferItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.article_title_view)
    TextView mTitleView;

    @BindView(R.id.article_description_view)
    TextView mDescriptionView;

    @BindView(R.id.article_image_view)
    ImageView mImageView;

    private Consumer<View> mOnItemClickAction;

    private BaseImageLoader mImageLoader;

    private Drawable mPlaceHolderDrawable;

    public OfferItemViewHolder(View itemView, BaseImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mImageLoader = imageLoader;
        mPlaceHolderDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_image_holder);
    }

    public void bindArticle(OfferItem offerItem) {
        Article article = offerItem.getArticle();
        mTitleView.setText(article.getTitle());
        mDescriptionView.setText(article.getDescription());
        mImageView.setVisibility(View.GONE);
        mImageLoader.loadImage(mImageView, article.getImageHref(), null, succeed -> {
            mImageView.setVisibility(succeed ? View.VISIBLE : View.GONE);
        });
        mOnItemClickAction = offerItem.getOnClickAction();
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        ViewUtils.setArticleTransitionName(mImageView.getContext(), mImageView, article.getId());
    }

    @Override
    public void onClick(View view) {
        try {
            mOnItemClickAction.accept(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
