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
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.ui.basic.BaseFragment;
import me.li2.android.architecture.ui.offerdetail.viewmodel.OfferDetailViewModel;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.ViewUtils;

public class OfferDetailFragment extends BaseFragment {

    public static final String EXTRA_OFFER_ID = "offer_id";

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

    public static OfferDetailFragment newInstance(String offerId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_OFFER_ID, offerId);
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
        return R.layout.offer_detail_fragment;
    }

    @Override
    public void setupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.getOffer(getOfferId()).observe(this, offer -> {
            if (offer != null) {
                bindOffer(offer);
            }
        });
    }

    private void bindOffer(Offer offer) {
        mTitleView.setText(offer.name);
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        ViewUtils.setArticleTransitionName(mImageView.getContext(), mImageView, getOfferId());
        // TODO need check null
        mImageLoader.loadImage(mImageView, offer.images.get(0).cloudinaryId, null, succeed -> startPostponedEnterTransition());
    }

    private String getOfferId() {
        if (getArguments() != null) {
            return getArguments().getString(EXTRA_OFFER_ID);
        }
        return null;
    }
}
