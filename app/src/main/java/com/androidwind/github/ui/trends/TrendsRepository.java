package com.androidwind.github.ui.trends;

import com.androidwind.androidquick.module.exception.ApiException;
import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubEvent;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.mvvm.BaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TrendsRepository extends BaseRepository {

    private MutableLiveData<Data<List<GithubEvent>>> liveDataGithubEvent =  new MutableLiveData<>();

    public MutableLiveData<Data<List<GithubEvent>>> getLiveDataGithubEvent() {
        return liveDataGithubEvent;
    }

    public LiveData<Data<List<GithubEvent>>> getTrends(String name, int page, int per_page) {
        if (page == 1) {
            liveDataGithubEvent.setValue(Data.loading());
        }
        RetrofitApi.getRepoApi()
                .getTrends(true, name, page, per_page)
                .compose(RxUtil.io2Main())
                .subscribe(new BaseObserver<List<GithubEvent>>() {
                    @Override
                    public void onError(ApiException exception) {
                        liveDataGithubEvent.setValue(Data.error(exception.getMsg()));
                    }

                    @Override
                    public void onSuccess(List<GithubEvent> githubEvents) {
                        liveDataGithubEvent.setValue(Data.success(githubEvents));
                    }
                });
        return liveDataGithubEvent;
    }

}
