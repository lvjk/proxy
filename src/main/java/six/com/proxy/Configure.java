package six.com.proxy;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月8日 上午10:06:30
 */
public class Configure {

	final static Logger LOG = LoggerFactory.getLogger(Configure.class);

	private Properties properties;

	public String LISTEN_IP;
	public int LISTEN_PORT;
	public String USERNAME;
	public String PASSWORD;
	public int PROXY_WORKER_THREADS;

	private Configure() {
		properties = new Properties();
		FileInputStream in;
		String configPath = ProxyServer.class.getClassLoader().getResource("config.properties").getPath();
		try {
			in = new FileInputStream(configPath);
			properties.load(in);
			in.close();
		} catch (Exception e) {
			LOG.error("read config.properties err");
			System.exit(1);
		}
		LISTEN_IP = properties.getProperty("listen.ip");
		if (StringUtils.isBlank(LISTEN_IP)) {
			LOG.error("please set listen.ip");
			System.exit(1);
		}
		String listenPortStr = properties.getProperty("listen.port");
		if (StringUtils.isBlank(listenPortStr)) {
			LOG.error("please set listen.port");
			System.exit(1);
		}
		try {
			LISTEN_PORT = Integer.valueOf(listenPortStr);
		} catch (Exception e) {
			LOG.error("set listen.port is invalid");
			System.exit(1);
		}

		USERNAME = properties.getProperty("userName");
		if (StringUtils.isBlank(USERNAME)) {
			LOG.error("please set userName");
			System.exit(1);
		}

		PASSWORD = properties.getProperty("passWord");
		if (StringUtils.isBlank(PASSWORD)) {
			LOG.error("please set passWord");
			System.exit(1);
		}

		String proxyWorkerThreadsStr = properties.getProperty("proxyWorkerThreads");
		if (StringUtils.isBlank(listenPortStr)) {
			LOG.error("please set listen.port");
			System.exit(1);
		}
		try {
			PROXY_WORKER_THREADS = Integer.valueOf(proxyWorkerThreadsStr);
		} catch (Exception e) {
			LOG.error("set listen.port is invalid");
			System.exit(1);
		}
	}

	static class InsideConfigure {
		static Configure configure = new Configure();
	}

	public static Configure instance() {
		return InsideConfigure.configure;
	}

}
