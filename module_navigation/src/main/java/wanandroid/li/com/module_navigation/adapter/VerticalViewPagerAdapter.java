package wanandroid.li.com.module_navigation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import wanandroid.li.com.module_navigation.R;
import wanandroid.li.com.module_navigation.customview.QTabView;
import wanandroid.li.com.module_navigation.customview.TabAdapter;


/**
 * Created by liguangze on 2018/8/22.
 */

public class VerticalViewPagerAdapter  implements TabAdapter {

private Context mContext;
private List<String> data ;
    public VerticalViewPagerAdapter(FragmentActivity activity, List<String> tabtitle) {
        this.mContext =  activity ;
        this.data = tabtitle ;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public int getBadge(int position) {
        if (position == data.size()) return position;
        return 0;
    }

    @Override
    public QTabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public QTabView.TabTitle getTitle(int position) {
        return new QTabView.TabTitle.Builder(mContext)
                .setContent(data.get(position))
                .setTextColor(mContext.getResources().getColor(R.color.redlight), Color.BLACK)
                .setTextSize(mContext.getResources().getDimensionPixelOffset(R.dimen.sp_pager))
                .build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
