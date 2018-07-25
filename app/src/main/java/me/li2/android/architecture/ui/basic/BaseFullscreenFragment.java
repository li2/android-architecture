package me.li2.android.architecture.ui.basic;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public abstract class BaseFullscreenFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enterFullscreen((AppCompatActivity) getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        exitFullscreen((AppCompatActivity) getActivity());
    }

    private void enterFullscreen(AppCompatActivity activity) {
        activity.getSupportActionBar().hide();
        activity.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void exitFullscreen(AppCompatActivity activity) {
        activity.getSupportActionBar().show();
        activity.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }
}
