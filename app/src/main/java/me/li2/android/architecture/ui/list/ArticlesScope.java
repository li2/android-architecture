package me.li2.android.architecture.ui.list;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Why Dagger2 inject the same object but with 2 different instances?
 * https://stackoverflow.com/a/50227726/2722270
 * @author Weiyi Li on 7/5/18 | https://github.com/li2
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ArticlesScope {
}
