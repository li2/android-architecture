package me.li2.android.architecture.ui.offers.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.offers.viewmodel.OfferItem;
import me.li2.android.architecture.utils.ImageLoader;

/**
 *
 -- To use MVP for RecyclerView,
 Normally we create a Collection (let it be a List) field within the adapter, holding all the data that it needs to display.
 This sucks, because in MVP we typically manager data in presenter,
 this makes the list to be referenced (or worse: copied) in two different places,
 which doubles our effort to keep those two in sync when making changes.
 Instead, we should use presenter ! --

 The above is no longer applicable with MVVM pattern

 https://android.jlelse.eu/recyclerview-in-mvp-passive-views-approach-8dd74633158

 * @author Weiyi Li on 6/5/18 | https://github.com/li2
 */

public class OffersAdapter extends RecyclerView.Adapter<OfferItemViewHolder> {

    private List<OfferItem> mArticles;

    @Inject
    ImageLoader mImageLoader;

    @Inject
    public OffersAdapter() {
    }


    public void refreshData(List<OfferItem> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArticles != null ? mArticles.size() : 0;
    }

    @Override
    public OfferItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_view_holder, parent, false);
        return new OfferItemViewHolder(view, mImageLoader);
    }

    @Override
    public void onBindViewHolder(OfferItemViewHolder holder, int position) {
        holder.bindArticle(mArticles.get(position));
    }
}
