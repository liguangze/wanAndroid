package wanandroid.li.com.module_navigation.customview;

import android.content.Context;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public abstract class TabView extends FrameLayout implements Checkable {

    public TabView(Context context) {
        super(context);
    }

    public abstract TabView setBadge(int num);
}
