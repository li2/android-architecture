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
import me.li2.android.architecture.ui.Config;
import me.li2.android.architecture.ui.basic.BaseFragment;
import me.li2.android.architecture.ui.offerdetail.viewmodel.OfferDetailItem;
import me.li2.android.architecture.ui.offerdetail.viewmodel.OfferDetailViewModel;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.ViewUtils;

public class OfferDetailFragment extends BaseFragment {

    public static final String EXTRA_OFFER_ID = "offer_id";

    @Inject
    OfferDetailViewModel mViewModel;

    @Inject
    BaseImageLoader mImageLoader;

    @BindView(R.id.offer_image_view)
    ImageView mImageView;

    @BindView(R.id.offer_name_view)
    TextView mNameView;
    
    @BindView(R.id.offer_description_view)
    TextView mDescriptionView;

    @BindView(R.id.offer_location_view)
    TextView mOfferLocationView;

    @BindView(R.id.offer_price_from_view)
    TextView mOfferMinPriceView;

    @BindView(R.id.offer_price_upto_view)
    TextView mOfferMaxPriceView;

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

        mViewModel.getOfferDetailItem(getOfferId()).observe(this, offer -> {
            if (offer != null) {
                bindOfferDetailItem(offer);
            }
        });
    }

    private void bindOfferDetailItem(OfferDetailItem item) {
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        ViewUtils.setOfferTransitionName(mImageView.getContext(), mImageView, getOfferId());
        mImageLoader.loadImage(mImageView, Config.photoUrl(item.photoCloudinaryId), null,
                succeed -> startPostponedEnterTransition());

        mNameView.setText(item.name);
        mDescriptionView.setText(item.description);
        mOfferLocationView.setText(item.location);
        mOfferMinPriceView.setText(item.minPrice);
        mOfferMaxPriceView.setText(item.maxPrice);
    }

    private String getOfferId() {
        if (getArguments() != null) {
            return getArguments().getString(EXTRA_OFFER_ID);
        }
        return null;
    }
}
