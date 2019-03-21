package wanandroid.li.com.common_base.utils;

import android.util.Log;

import wanandroid.li.com.common_base.BuildConfig;


/**
 *  Created by liguangze on 2017/6/21.
 */
public class L {


    private final static String TAG = "tag";
    private final static boolean DEBUG = BuildConfig.DEBUG;
    private final static boolean CANCEL_TAG = false;

    public static void i(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            if (!CANCEL_TAG) {
                Log.i(tag, message);
            } else {
                i(message);
            }
        }
    }

    public static void w(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG) {
            if (!CANCEL_TAG) {
                Log.w(tag, message);
            } else {
                w(message);
            }
        }
    }

    public static void e(String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            if (!CANCEL_TAG) {
                Log.i(tag, message);
            } else {
                e(message);
            }
        }
    }
}
