
package me.li2.android.architecture.ui.offers.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.offerdetail.view.OfferDetailFragment;
import me.li2.android.architecture.ui.widget.PhotosActivity;
import me.li2.android.architecture.utils.BaseNavigator;

/**
 * Defines the navigation actions that can be called from the offer list screen.
 */
public class OffersNavigator {

    @Inject
    BaseNavigator mNavigator;

    @Inject
    public OffersNavigator() {

    }

    /**
     * Open the details of an offer.
     */
    public void openOfferDetails(String id, @NonNull View sharedElement) {
        mNavigator.addFragment(OfferDetailFragment.newInstance(id), R.id.fragmentContainer, sharedElement);
    }

    public void openPhoto(String title, int position, List<String> photoCloudinaryIds) {
        Bundle args = new Bundle();
        args.putString(PhotosActivity.EXTRA_PHOTOS_TITLE, title);
        args.putInt(PhotosActivity.EXTRA_PHOTO_INDEX, position);
        args.putStringArrayList(PhotosActivity.EXTRA_PHOTOS_CLOUDINARYIDS, new ArrayList<>(photoCloudinaryIds));
        mNavigator.startActivityForResultWithExtra(PhotosActivity.class, -1, args);
    }
}
