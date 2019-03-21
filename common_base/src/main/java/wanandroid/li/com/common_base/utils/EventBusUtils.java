package wanandroid.li.com.common_base.utils;


import org.greenrobot.eventbus.EventBus;

import wanandroid.li.com.common_base.baseBean.Event;


/**
 * Describe：EventBusUtils
 */

public class EventBusUtils {

    /**
     * 注册事件
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解除事件
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送普通事件
     */
    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }
    //...
}
