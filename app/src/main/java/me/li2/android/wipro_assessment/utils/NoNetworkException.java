package me.li2.android.wipro_assessment.utils;

import java.io.IOException;

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

public class NoNetworkException extends IOException {
    @Override
    public String getMessage() {
        return "No network connectivity exception";
    }
}
