
package me.li2.android.architecture.ui.offers.view;

import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.offerdetail.view.OfferDetailFragment;
import me.li2.android.architecture.utils.BaseNavigator;

/**
 * Defines the navigation actions that can be called from the articles list screen.
 */
public class OffersNavigator {

    @Inject
    BaseNavigator mNavigator;

    @Inject
    public OffersNavigator() {

    }

    /**
     * Open the details of an article.
     */
    public void openArticleDetails(String id, @NonNull View sharedElement) {
        mNavigator.addFragment(OfferDetailFragment.newInstance(id), R.id.fragmentContainer, sharedElement);
    }
}
