package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.li2.android.wipro_assessment.R;
import me.li2.android.wipro_assessment.data.model.CountryIntroEntry;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */

public class CountryIntroListAdapter extends RecyclerView.Adapter<CountryIntroViewHolder> {
    private Context mContext;
    private List<CountryIntroEntry> mIntros;

    public CountryIntroListAdapter(Context context) {
        mContext = context;
    }

    public void update(List<CountryIntroEntry> intros) {
        mIntros = intros;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (mIntros != null) ? mIntros.size() : 0;
    }

    @Override
    public CountryIntroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.country_intro_view_holder, parent, false);
        return new CountryIntroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryIntroViewHolder holder, int position) {
        holder.bindCountryIntro(mIntros.get(position));
    }
}
