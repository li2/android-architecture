package me.li2.android.architecture.ui.offerdetail.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.ui.offerdetail.viewmodel.OfferDetailViewModel;
import me.li2.android.architecture.ui.basic.BaseFragment;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.ViewUtils;

public class OfferDetailFragment extends BaseFragment {

    public static final String EXTRA_ARTICLE_ID = "article_id";

    @Inject
    OfferDetailViewModel mViewModel;

    @Inject
    BaseImageLoader mImageLoader;

    @BindView(R.id.article_image_view)
    ImageView mImageView;

    @BindView(R.id.article_title_view)
    TextView mTitleView;
    
    @BindView(R.id.article_description_view)
    TextView mDescriptionView;

    public static OfferDetailFragment newInstance(int articleId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ARTICLE_ID, articleId);
        OfferDetailFragment fragment = new OfferDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // shared element transition between RecyclerView and Fragment
        // tell the Fragment to wait to load until we tell it
        postponeEnterTransition();
        // tell it what type of Transition we want
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.article_detail_fragment;
    }

    @Override
    public void setupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.getArticle(getArticleId()).observe(this, article -> {
            if (article != null) {
                bindArticle(article);
            }
        });
    }

    private void bindArticle(Article article) {
        mTitleView.setText(article.getTitle());
        mDescriptionView.setText(article.getDescription());
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        ViewUtils.setArticleTransitionName(mImageView.getContext(), mImageView, getArticleId());
        mImageLoader.loadImage(mImageView, article.getImageHref(), null, succeed -> startPostponedEnterTransition());
    }

    private int getArticleId() {
        if (getArguments() != null) {
            return getArguments().getInt(EXTRA_ARTICLE_ID);
        }
        return -1;
    }
}
