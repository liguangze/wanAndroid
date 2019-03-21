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
package wanandroid.li.com.common_base.http;


import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import wanandroid.li.com.common_base.base.BaseProgressObserver;
import wanandroid.li.com.common_base.error.IResponseErrorMsg;
import wanandroid.li.com.common_base.http.conver.GsonConverterFactory;

/**
 * RetrofitClient
 * 网络基类
 * 在Application里面初始化
 */


public class RetrofitClient {
	private static final String TAG = "Retrofit";


	private static RetrofitClient instance;
	private Retrofit client;


	private IResponseErrorMsg errorResponse;

	private INetWorkLoadingView loadingView;

	private IRefreshToken iRefreshToken;

	private ProxyHandler proxyHandler;

	private BaseProgressObserver progressObserver;

	public void setProgressObserver(BaseProgressObserver progressObserver) {
		this.progressObserver = progressObserver;
	}

	/**
	 * 初始化网络
	 *
	 * @param config RetrofitClient 配置文件
	 */
	public static void init(RetrofitConfig config) {
		initRetrofitClient(config);
	}

	/**
	 * 内部使用
	 */
	public IResponseErrorMsg getErrorResponse() {
		return errorResponse;
	}

	/**
	 * 内部使用
	 */
	public INetWorkLoadingView getLoadingView() {
		return loadingView;
	}

	/**
	 * init Retrofit
	 *
	 * @param config
	 */
	private RetrofitClient(RetrofitConfig config) {
		errorResponse = config.getiResponseErrorMsg();
		loadingView = config.getLoadingView();
		iRefreshToken = config.getiRefreshToken();
		if (config.getGsonClass() == null) {
			throw new NullPointerException("gsonClazz is null");
		}
		if (iRefreshToken != null) proxyHandler = new ProxyHandler(iRefreshToken);
		OkHttpClient.Builder builder = config.getBuilder() == null ? new OkHttpClient.Builder() : config.getBuilder();
//		builder.addInterceptor(progressInterceptor());
		client = new Retrofit.Builder()
				.client(builder.build())
				.baseUrl(config.getBaseUrl())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(config.getGsonClass()))
//				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}

	public static RetrofitClient getInstance() {
		if (instance == null) {
			throw new NullPointerException("you need initialize RetrofitClient");
		}
		return instance;
	}


	private static RetrofitClient initRetrofitClient(RetrofitConfig retrofitConfig) {
		if (instance == null) {
			synchronized (RetrofitClient.class) {
				if (instance == null) {
					instance = new RetrofitClient(retrofitConfig);
				}
			}
		}
		return instance;
	}

	public <T> T createApi(Class<? extends T> clazz) {
		T t = client.create(clazz);
		if (iRefreshToken == null) {
			return t;
		}
		proxyHandler.setObject(t);
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxyHandler);
	}

	public <T> T createRefreshToken(Class<? extends T> clazz) {
		return client.create(clazz);
	}



}
