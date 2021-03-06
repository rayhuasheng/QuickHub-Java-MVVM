package com.androidwind.github.ui.starred;

import com.androidwind.androidquick.module.exception.ApiException;
import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubRepository;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.mvvm.BaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class StarredRepository extends BaseRepository {

    private MutableLiveData<Data<List<GithubRepository>>> liveDataGithubRepository = new MutableLiveData<>();

    public MutableLiveData<Data<List<GithubRepository>>> getLiveDataGithubRepository() {
        return liveDataGithubRepository;
    }

    public LiveData<Data<List<GithubRepository>>> getStarred(int page, int per_page) {
        if (page == 1) {
            liveDataGithubRepository.setValue(Data.loading());
        }
        RetrofitApi.getUserApi()
                .getGithubStarred(page, per_page)
                .compose(RxUtil.io2Main())
                .subscribe(new BaseObserver<List<GithubRepository>>() {
                    @Override
                    public void onError(ApiException exception) {
                        liveDataGithubRepository.setValue(Data.error(exception.getMsg()));
                    }

                    @Override
                    public void onSuccess(List<GithubRepository> githubUser) {
                        liveDataGithubRepository.setValue(Data.success(githubUser));
                    }
                });
        return liveDataGithubRepository;
    }

}
