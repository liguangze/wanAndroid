package wanandroid.li.com.module_navigation.customview;


/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public interface TabAdapter {
    int getCount();

    int getBadge(int position);

    QTabView.TabIcon getIcon(int position);

    QTabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
