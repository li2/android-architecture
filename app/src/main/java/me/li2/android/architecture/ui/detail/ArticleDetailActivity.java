package me.li2.android.architecture.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.utils.InjectorUtils;

public class ArticleDetailActivity extends AppCompatActivity {

    private static final String EXTRA_ARTICLE_ID = "article_id";

    private ArticleDetailViewModel mViewModel;
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

        ArticleDetailViewModelFactory factory = InjectorUtils.provideArticleDetailViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(ArticleDetailViewModel.class);
        mViewModel.getArticle(mArticleId).observe(this, article -> {
            if (article != null) {
                bindArticle(article);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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
