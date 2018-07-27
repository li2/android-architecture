package me.li2.android.architecture.ui.articles.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import butterknife.BindView;
import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.basic.BaseSingleFragmentActivity;

public class ArticlesActivity extends BaseSingleFragmentActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitleView;

    @Override
    protected Fragment createFragment() {
        return new ArticlesFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        String title = "LUXURYESCAPES";
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
        ssBuilder.setSpan(
                new StyleSpan(Typeface.BOLD),
                title.indexOf("ESCAPES"),
                title.indexOf("ESCAPES") + String.valueOf("ESCAPES").length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mToolbarTitleView.setText(ssBuilder);
    }
}
