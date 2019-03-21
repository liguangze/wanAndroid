package wanandroid.li.com.module_main.activity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.li.wanandroid.module_main.R;
import com.li.wanandroid.module_main.R2;

import butterknife.BindView;
import wanandroid.li.com.common_base.base.BaseMvpActivity;
import wanandroid.li.com.common_base.base.BasePresenter;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.common_base.view.ActionBar;
import wanandroid.li.com.common_base.widght.ProgressWebView;

/**
 * Created by liguangze on 2019/3/21.
 */

public class WebViewActivity extends BaseMvpActivity {

    @BindView(R2.id.pw_view)
    ProgressWebView webView;
    private ActionBar mActionBar;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {

        webView.loadUrl(getIntent().getStringExtra("url"));
        webView.setWebViewClient(new MyWebClient());

        mActionBar = findViewById(R.id.actionbar);

    }


    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // 接受所有网站的证书
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            mActionBar.setCenterText(view.getTitle()+"");
//            setTitleText(view.getTitle());
        }
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showFinish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    private void showFinish() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

}
