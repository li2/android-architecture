package me.li2.android.architecture.ui.basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import me.li2.android.architecture.utils.BaseResourceProvider;

/**
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public abstract class BaseFragment extends DaggerFragment {

    @Inject
    protected BaseResourceProvider mResourceProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);

        setupView(inflater, container, savedInstanceState);

        return rootView;
    }


    /* notebyweiyi: onViewCreated(...) Called immediately after onCreateView(...) has returned
     Fragment added in fragment xml layout, then they will be available after calling fragments onViewCreated method */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public abstract int getLayout();

    public abstract void setupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
