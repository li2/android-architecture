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
import me.li2.android.architecture.ui.Config;
import me.li2.android.architecture.ui.offers.viewmodel.OfferItem;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.ViewUtils;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */
public class OfferItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
    TextView mMinPriceView;

    @BindView(R.id.offer_item_price_upto_view)
    TextView mMaxPriceView;

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

    public void bindOffer(OfferItem item) {
        mTitleView.setText(item.name);

        mLocationView.setText(item.location);

        if (item.isHotelVisible) {
            mHotelView.setText(item.hotelLocation);
            mNightsRangeView.setText(item.hotelNightsRange);
            mHotelView.setVisibility(View.VISIBLE);
            mNightsRangeView.setVisibility(View.VISIBLE);
        } else {
            mHotelView.setVisibility(View.GONE);
            mNightsRangeView.setVisibility(View.GONE);
        }

        mOfferEndView.setText(item.offerEnds);

        mMinPriceView.setText(item.minPrice);

        mMaxPriceView.setText(item.maxPrice);

        mImageLoader.loadImage(mImageView, Config.photoUrl(item.photoCloudinaryId), null);
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        ViewUtils.setOfferTransitionName(mImageView.getContext(), mImageView, item.idSalesforceExternal);

        mOnItemClickAction = item.onClickAction;
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
