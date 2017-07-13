package six.com.proxy;

import org.apache.commons.lang3.StringUtils;
import org.littleshoot.proxy.ProxyAuthenticator;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月8日 上午10:05:44
 */
public class MyProxyAuthenticator implements ProxyAuthenticator {

	private String userName = Configure.instance().USERNAME;
	private String passWord = Configure.instance().PASSWORD;

	@Override
	public boolean authenticate(String userName, String password) {
		if (StringUtils.equals(userName, this.userName) 
				&& StringUtils.equals(password, this.passWord)) {
			return true;
		}
		return false;
	}

	@Override
	public String getRealm() {
		return null;
	}
}
