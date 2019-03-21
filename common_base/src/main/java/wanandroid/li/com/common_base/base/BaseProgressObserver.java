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


import io.reactivex.disposables.Disposable;
import wanandroid.li.com.common_base.manager.RxManager;

/**
 * 带进度的Observer 用于上传文件
 * @author location
 */

public abstract class BaseProgressObserver<T> extends BaseObserver<T> {
	private long contentLength;
	private Disposable disposable;

	public Disposable getDisposable() {
		return disposable;
	}

	public BaseProgressObserver(RxManager rxManager, BaseView baseView) {
		super(rxManager, baseView);
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public void onSubscribe(Disposable d) {
		super.onSubscribe(d);
		this.disposable = d;
	}

	@Override
	public void onNext(T t) {
		uploadSuccful(t, contentLength);
	}

	public abstract void onProgress(long readWrite, long contentLength);

	public abstract void uploadSuccful(T response, long contentLength);
}
