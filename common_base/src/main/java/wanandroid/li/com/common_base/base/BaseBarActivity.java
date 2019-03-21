package wanandroid.li.com.common_base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import wanandroid.li.com.common_base.R;
import wanandroid.li.com.common_base.view.ActionBar;


/**
 * description：
 */

public abstract class BaseBarActivity<P extends BasePresenter> extends BaseMvpActivity<P> {
	protected ActionBar actionBar;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionBar = findViewById(R.id.actionbar);
	}

	protected void setTitleText(String title) {
		if (actionBar != null) {
			actionBar.setCenterText(title);
		}
	}

	protected void setTitleText(int title) {
		if (actionBar != null) {
			actionBar.setCenterText(getString(title));
		}
	}

	@Override
	protected boolean isActionBar() {
		return true;
	}
}
