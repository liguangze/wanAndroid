package wanandroid.li.com.common_base.baseBean;


import wanandroid.li.com.common_base.http.IBaseBean;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:31
 * description：
 */

public class BaseData<T> implements IBaseBean<T> {
	private int errorCode;
	private String errorMsg;
	private T data;

	@Override
	public T getData() {
		return data;
	}

	@Override
	public boolean isOk() {
		return errorCode==0;
	}

	@Override
	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public int getStatusCode() {
		return errorCode;
	}
}
