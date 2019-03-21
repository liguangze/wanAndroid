package wanandroid.li.com.common_base.baseBean;


import wanandroid.li.com.common_base.error.IResponseErrorMsg;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 13:29
 * description：
 */

public class ErrorResponse implements IResponseErrorMsg {

	@Override
	public String getErrorMsg(int errcode) {
		String msg = "";
		switch(errcode){
			case -1:
				msg=  "好像账号不对啊";
				default:
		}
		return msg;
	}
}
