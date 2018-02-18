package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.wipro_assessment.R;
import me.li2.android.wipro_assessment.data.database.CountryIntroEntry;
import okhttp3.OkHttpClient;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */

public class CountryIntroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String LOG_TAG = CountryIntroViewHolder.class.getSimpleName();

    private Context mContext;
    private CountryIntroEntry mCountryIntro;

    @BindView(R.id.country_intro_title_view)
    TextView mTitleView;

    @BindView(R.id.country_intro_description_view)
    TextView mDescriptionView;

    @BindView(R.id.country_intro_image_view)
    ImageView mImageView;

    public CountryIntroViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mContext = itemView.getContext();
    }

    public void bindCountryIntro(CountryIntroEntry intro) {
        if (intro == null) {
            return;
        }
        mCountryIntro = intro;
        mTitleView.setText(intro.getTitle());
        mDescriptionView.setText(intro.getDescription());

        // Picasso is that it automatically takes care of the request canceling, clearing of the ImageViews, and
        // loading the correct image into the appropriate ImageView.
        // Picasso uses three sources: memory, disk and network (ordered from fastest to slowest). and
        // there is nothing we'll have to do.

        // notebyweiyi: fix image cannot download issue that com.squareup.picasso.Downloader$ResponseException: 301 TLS Redirect
        Picasso picasso = new Picasso.Builder(mContext)
                .loggingEnabled(true)
                .downloader(new OkHttp3Downloader(new OkHttpClient()))
                .listener((picasso1, uri, exception) -> exception.printStackTrace())
                .build();

        mImageView.setVisibility(View.GONE);

        picasso.load(intro.getImageHref())
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mImageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        mImageView.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), mCountryIntro.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
    }
}
