package me.li2.android.architecture.ui.articledetail.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.ui.articledetail.viewmodel.ArticleDetailViewModel;

public class ArticleDetailActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_ARTICLE_ID = "article_id";

    // error: Dagger does not support injection into private fields
    @Inject
    ArticleDetailViewModel mViewModel;

    private int mArticleId;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.article_image_view)
    ImageView mImageView;

    @BindView(R.id.article_description_view)
    TextView mDescriptionView;

    public static Intent newIntent(Context packageContext, int articleId) {
        Intent intent = new Intent(packageContext, ArticleDetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, articleId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail_activity);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            mArticleId = getIntent().getIntExtra(EXTRA_ARTICLE_ID, -1);
        }

        mViewModel.getArticle(mArticleId).observe(this, article -> {
            if (article != null) {
                bindArticle(article);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // activity animation will be lose if finish() here !
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindArticle(Article article) {
        // Toolbar.setTitle() not works if we use CollapsingToolbarLayout
        mToolbarLayout.setTitle(article.getTitle());
        mDescriptionView.setText(article.getDescription());
        Picasso.with(this)
                .load(article.getImageHref())
                .placeholder(R.drawable.ic_android_architecture)
                .error(R.drawable.ic_android_architecture)
                .into(mImageView);
    }
}
