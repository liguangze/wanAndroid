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
package wanandroid.li.com.common_base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.TextureView;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * SharedPreferences存储工具类  简化了sp存储方式
 * 并且支持实体类的保存
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class SpUtils {

	/**
	 * SharedPreferences 对象
	 */
	private static SharedPreferences sharedPreferences;
	/**
	 * 上下文
	 */
	private static Context context;
	/**
	 * 存储SharedPreferences对象  每个key值对应一个
	 */
	private static ArrayMap<String, SharedPreferences> sps = new ArrayMap<>();
	/**
	 * 单例模式
	 */
	private static SpUtils spUtils;

	/**
	 * 查找String的默认字符串
	 */
	private static final String DEFAULT_STRING = "";
	/**
	 * 查找Boolean的默认值
	 */
	private static final boolean DEFAULT_BOOLEAN = false;
	/**
	 * 查找Int类型的默认值
	 */
	private static final int DEFAULT_INT = -1;
	/**
	 * 查找Set集合的默认值
	 */
	private static final Set<String> DEFAULT_SET = new HashSet<>();
	/**
	 * 查找long类型的默认值
	 */
	private static final long DEFAULT_LONG = -1;
	/**
	 * 查找float的默认值
	 */
	private static final float DEFAULT_FLOAT = -1f;

	/**
	 * 构造私有化
	 *
	 * @param context
	 */
	private SpUtils(Context context) {
		SpUtils.context = context;
		/**
		 * 默认的key值为app名字
		 * 没有获取到名字 获取包名
		 */
		sharedPreferences = context.getSharedPreferences(getAppName(context), Context.MODE_PRIVATE);
		/**
		 * 存储到集合
		 */
		sps.put(getAppName(context), sharedPreferences);
	}

	/**
	 * 初始化方法  在Application中初始化 防止内存泄露
	 *
	 * @param context
	 */
	public static void init(Context context) {
		spUtils = new SpUtils(context);
	}

	public static SpUtils getInstance(String keys) {
		/**
		 * 如果上下文为空 没有初始化
		 * 抛出异常
		 * throw {@link NullPointerException}
		 */
		if (context == null) throw new NullPointerException("you may Application init");
		/**
		 * 判断当前key是否为空 为空则第一次加载  实例化
		 */
		if (sps.get(keys) == null) {
			sharedPreferences = context.getSharedPreferences(keys, Context.MODE_PRIVATE);
			sps.put(keys, sharedPreferences);
		} else {
			//反之获取sharedPreferences对象
			sharedPreferences = sps.get(keys);
		}
		return spUtils;
	}

	public static SpUtils getInstance() {
		if (context == null) throw new NullPointerException("you may Application init");
		sharedPreferences = sps.get(getAppName(context));
		return spUtils;
	}

	/**
	 * 下面的重载方法都是存储在
	 *
	 * @param key
	 * @param value
	 */
	public void putValue(String key, String value) {
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		//使用apply降低内存开销
		edit.apply();
	}

	public void putValue(String key, int value) {
		sharedPreferences.edit().putInt(key, value).apply();
	}

	public void putValue(String key, boolean value) {
		sharedPreferences.edit().putBoolean(key, value).apply();
	}

	public void putValue(String key, long value) {
		sharedPreferences.edit().putLong(key, value).apply();
	}

	public void putValue(String key, float value) {
		sharedPreferences.edit().putFloat(key, value).apply();
	}

	public void putValue(String key, Set<String> value) {
		sharedPreferences.edit().putStringSet(key, value).apply();
	}


	public void putArray(String key,Object object){
		String tojson = JsonUtils.obtJson(object);
		if (TextUtils.isEmpty(tojson)) {
			return;
		}
		sharedPreferences.edit().putString(key, tojson).apply();
	}
	/**
	 * 存储Object类型
	 * 方法内会检查项目是否存在Gson包
	 * 如果存在就会使用Gson toJson方法转化成字符串存储
	 *
	 * @param key
	 * @param object
	 */
	public void putValue(String key, Object object) {
		String tojson = JsonUtils.obtJson(object);
		if (TextUtils.isEmpty(tojson)) {
			return;
		}
		sharedPreferences.edit().putString(key, tojson).apply();
	}


	public void putValue(Object object) {
		putValue(object.getClass().getSimpleName(), object);
	}

	/**
	 * 下方都是获取值
	 *
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return sharedPreferences.getString(key, DEFAULT_STRING);
	}

	public boolean getBoolean(String key) {
		return sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN);
	}

	public int getInt(String key) {
		return sharedPreferences.getInt(key, DEFAULT_INT);
	}

	public Set<String> getStringSet(String key) {
		return sharedPreferences.getStringSet(key, DEFAULT_SET);
	}

	public long getLong(String key) {
		return sharedPreferences.getLong(key, DEFAULT_LONG);
	}

	public float getFloat(String key) {
		return sharedPreferences.getFloat(key, DEFAULT_FLOAT);
	}


	public <T> T getObject(String key, Class<? extends T> clazz) {
		String str = sharedPreferences.getString(key, DEFAULT_STRING);
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		return JsonUtils.obtObject(key, clazz);
	}


	public <T> T getObject(Class<? extends T> clazz) {
		return getObject(clazz.getSimpleName(), clazz);
	}


	public <T> List<T> getArray(String key,Class<? extends T> clazz){
		String str = sharedPreferences.getString(key, DEFAULT_STRING);
		if(TextUtils.isEmpty(str)){
			return null;
		}
		return JsonUtils.obtArray(str,clazz);
	}

	/**
	 * 判断是否包含某个key值
	 *
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		return sharedPreferences.contains(key);
	}

	/**
	 * 清除掉数据
	 */
	public void clearAll() {
		sharedPreferences.edit().clear().apply();
	}

	/**
	 * 删除掉某个key值
	 *
	 * @param key
	 */
	public void remove(String key) {
		sharedPreferences.edit().remove(key).apply();
	}


	/**
	 * 获取所有存储的键值对
	 *
	 * @return
	 */
	public Map<String, ?> getAll() {
		return sharedPreferences.getAll();
	}


	private static String getAppName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			return context.getPackageName();
		}
	}




}
