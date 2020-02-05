package com.androidwind.github.ui.main;

import com.androidwind.base.module.BaseObserver;
import com.androidwind.base.module.exeception.ApiException;
import com.androidwind.base.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubBasicToken;
import com.androidwind.github.bean.GithubUser;
import com.androidwind.github.module.retrofit.AuthRequestModel;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.mvvm.BaseRepository;
import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainRepository extends BaseRepository {

    private final String TAG = "MainRepository";

    private MutableLiveData<Data<GithubUser>> liveDataUser;

    public LiveData<Data<GithubBasicToken>> loginWithToken(String token) {
        AuthRequestModel authRequestModel = AuthRequestModel.generate();
        getToken(token, authRequestModel);
        return liveDataLogin;
    }

    public LiveData<Data<GithubUser>> getGithubUser(String token) {
        liveDataUser =  new MutableLiveData<>();
        liveDataUser.setValue(Data.loading());
        RetrofitApi.getUserApi(token)
                .getUserInfo()
                .compose(RxUtil.applySchedulers())
                .subscribe(new BaseObserver<GithubUser>() {
                    @Override
                    public void onError(ApiException exception) {
                        LogUtils.eTag(TAG, exception.toString());//打印错误日志
                        liveDataUser.setValue(Data.error(exception.message));
                    }

                    @Override
                    public void onSuccess(GithubUser githubUser) {
                        liveDataUser.setValue(Data.success(githubUser));
                    }
                });
        return liveDataUser;
    }
}
