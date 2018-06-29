package com.jthou.wanandroid.util;

import com.jthou.wanandroid.model.network.AbstractResponse;
import com.jthou.wanandroid.model.network.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2018/5/21.
 */

public class RxUtil {


    public static <T> ObservableTransformer<T, T> schedulerHelper() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<AbstractResponse<T>, T> handleResponse() {
        return new ObservableTransformer<AbstractResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<AbstractResponse<T>> upstream) {
                return upstream.flatMap(new Function<AbstractResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(AbstractResponse<T> tAbstractResponse) throws Exception {
                        if (tAbstractResponse.getErrorCode() < 0 || tAbstractResponse.getData() == null) {
                            return Observable.error(new ApiException("服务器返回error : " + tAbstractResponse.getErrorMsg()));
                        } else {
                            return createData(tAbstractResponse.getData());
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }



}
