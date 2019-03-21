package wanandroid.li.com.common_base.baseBean;


import android.app.ProgressDialog;
import android.content.Context;

import wanandroid.li.com.common_base.http.INetWorkLoadingView;


/**
 *
 * @author：location time：2018/11/9 20:37
 * description：
 */

public class LoadingView implements INetWorkLoadingView {
private ProgressDialog dialog;
	@Override
	public void createLoadingView(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setTitle("加载中");
	}

	@Override
	public void showLoading() {
		dialog.show();
	}
	@Override
	public void dismissLoading() {
		dialog.dismiss();
	}
}
