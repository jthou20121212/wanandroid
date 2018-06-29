package com.jthou.wanandroid.di.component;

import com.jthou.wanandroid.ui.main.activity.MainActivity;
import com.jthou.wanandroid.di.scope.ActivityScope;
import com.jthou.wanandroid.ui.login.LoginActivity;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.ui.welcome.activity.WelcomeActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(ArticleDetailActivity activity);

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(WelcomeActivity activity);

}
