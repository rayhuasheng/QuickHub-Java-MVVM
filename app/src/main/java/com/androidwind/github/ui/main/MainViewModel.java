package com.androidwind.github.ui.main;

import android.app.Application;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubAuth;
import com.androidwind.github.bean.GithubUser;
import com.androidwind.github.mvvm.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainViewModel extends BaseViewModel<MainRepository> {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Data<GithubAuth>> login(String authorization) {
        return repository.loginWithToken(authorization);
    }

    public LiveData<Data<GithubUser>> getGithubUser() {
        return repository.getGithubUser();
    }
}