package me.li2.android.architecture.ui.basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import me.li2.android.architecture.R;
import me.li2.android.architecture.utils.BaseResourceProvider;

/**
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public abstract class BaseFragment extends DaggerFragment {

    private TextView mToolbarTitleView;

    @Inject
    protected BaseResourceProvider mResourceProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);

        setupActionBar(rootView.findViewById(R.id.toolbar));
        setupView(inflater, container, savedInstanceState);

        return rootView;
    }

    public abstract int getLayout();

    public abstract void setupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    private void setupActionBar(Toolbar toolbar) {
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            mToolbarTitleView = toolbar.findViewById(R.id.toolbar_title);
        }
    }

    public void setActionBarTitle(String title) {
        if (mToolbarTitleView != null) {
            mToolbarTitleView.setText(title);
        }
    }

    public void setActionBarTitle(SpannableStringBuilder ssBuilder) {
        if (mToolbarTitleView != null) {
            mToolbarTitleView.setText(ssBuilder);
        }
    }
}
