/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wanandroid.li.com.common_base.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.common_base.http.INetWorkLoadingView;
import wanandroid.li.com.common_base.http.RetrofitClient;
import wanandroid.li.com.common_base.manager.RxManager;


/**
 * @author location
 *         默认的RxJava  Observer 回调
 */


public abstract class BaseObserver<T> implements Observer<T> {
	private RxManager rxManager;
	private BaseView baseView;
	private INetWorkLoadingView loadingView;

	public BaseObserver(RxManager rxManager, BaseView baseView) {
		this.rxManager = rxManager;
		this.baseView = baseView;
		loadingView = RetrofitClient.getInstance().getLoadingView();
		if (loadingView != null) {
			Context context = null;
			if (baseView instanceof Activity) {
				context = (Activity) baseView;
			} else if (baseView instanceof Fragment) {
				if (((Fragment) baseView).getActivity() == null) {
					throw new RuntimeException("fragment must be bind Activity");
				}
				context = ((Fragment) baseView).getActivity();
			} else if (baseView instanceof android.app.Fragment) {
				if (((android.app.Fragment) baseView).getActivity() == null) {
					throw new RuntimeException("fragment must be bind Activity");
				}
				context = ((android.app.Fragment) baseView).getActivity();
			}
			if (context != null) {
				loadingView.createLoadingView(context);
			}
		}
	}

	@Override
	public void onSubscribe(Disposable d) {
		rxManager.add(d);
		if(baseView==null){
			rxManager.clear();
		}else{
			if (loadingView != null) {
				loadingView.showLoading();
			}
		}
	}

	@CallSuper
	@Override
	public void onError(Throwable e) {

		if (e instanceof ExceptionHandle.ResponseThrowable) {
			baseView.onShowError((ExceptionHandle.ResponseThrowable) e);
		}
		if (loadingView != null) loadingView.dismissLoading();
	}

	@Override
	public void onComplete() {
		if (loadingView != null) loadingView.dismissLoading();
	}
}
