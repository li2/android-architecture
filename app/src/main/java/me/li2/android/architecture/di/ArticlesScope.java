package me.li2.android.architecture.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Q: Why Dagger2 inject the same object but with 2 different instances?
 * A: Lack a scope and managing this scope.
 *
 * https://stackoverflow.com/a/50227726/2722270
 * Scoping with @ContributesAndroidInjector,
 * refer to https://proandroiddev.com/dagger-2-annotations-binds-contributesandroidinjector-a09e6a57758f
 *
 * @author Weiyi Li on 7/5/18 | https://github.com/li2
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ArticlesScope {
}
