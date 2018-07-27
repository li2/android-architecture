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
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.ui.offers.viewmodel.OfferItem;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.ViewUtils;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */
public class OfferItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String LOCATION_FORMAT = "%s, %s";
    private static final String NIGHTS_RANGE_FORMAT = "%d - %d Nights";
    private static final String OFFER_ENDS_IN_FORMAT = "OFFER ENDS IN %d DAYS";

    @BindView(R.id.offer_item_name_view)
    TextView mTitleView;

    @BindView(R.id.offer_item_location_view)
    TextView mLocationView;

    @BindView(R.id.offer_item_hotel_view)
    TextView mHotelView;

    @BindView(R.id.offer_item_nights_range_view)
    TextView mNightsRangeView;

    @BindView(R.id.offer_item_ends_view)
    TextView mOfferEndView;

    @BindView(R.id.offer_item_price_from_view)
    TextView mPriceMinView;

    @BindView(R.id.offer_item_price_upto_view)
    TextView mPriceMaxView;

    @BindView(R.id.offer_item_image_view)
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
        Offer offer = offerItem.getOffer();
        mTitleView.setText(offer.name);
        mLocationView.setText(String.format(LOCATION_FORMAT, offer.locationHeading, offer.locationSubheading));
        mHotelView.setText(offer.location);
        mNightsRangeView.setText(String.format(NIGHTS_RANGE_FORMAT, offer.minNumNights, offer.maxNumNights));
        mOfferEndView.setText(OFFER_ENDS_IN_FORMAT);
        mImageView.setVisibility(View.GONE);
        // TODO need check null here
        mImageLoader.loadImage(mImageView, offer.images.get(0).cloudinaryId, null, succeed -> {
            mImageView.setVisibility(succeed ? View.VISIBLE : View.GONE);
        });
        mOnItemClickAction = offerItem.getOnClickAction();
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        ViewUtils.setArticleTransitionName(mImageView.getContext(), mImageView, offer.idSalesforceExternal);
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
