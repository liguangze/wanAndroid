package wanandroid.li.com.common_base.baseBean;


import io.reactivex.Observable;
import wanandroid.li.com.common_base.http.IRefreshToken;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 12:23
 * description：
 */

public class RefreshToken implements IRefreshToken {
	@Override
	public synchronized Observable refreshToken() {
//			LoginService api = RetrofitClient.getInstance().createRefreshToken(LoginService.class);
//			return api.login("tianxiaolong", "123");
		return null;

	}



	@Override
	public boolean isTokenException(int code, String errorMsg) {
		if ("请先登录！".equals(errorMsg)) {
			return true;
		}
		return false;
	}
}
