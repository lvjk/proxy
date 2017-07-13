package six.com.proxy;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.littleshoot.proxy.impl.ThreadPoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年2月13日 上午10:21:47
 */
public class ProxyServer {

	final static Logger LOG = LoggerFactory.getLogger(ProxyServer.class);

	public static void main(String[] args) {
		new ProxyServer().start();
	}

	public void start() {
		InetSocketAddress listenInetSocketAddress = null;
		try {
			InetAddress inetAddress = InetAddress.getByName(Configure.instance().LISTEN_IP);
			listenInetSocketAddress = new InetSocketAddress(inetAddress, Configure.instance().LISTEN_PORT);
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
			LOG.error("configure listen inetAddress err", e);
			System.exit(1);
		}
		MyProxyAuthenticator myProxyAuthenticator = new MyProxyAuthenticator();
		HttpProxyServerBootstrap bootstrap = DefaultHttpProxyServer.bootstrap();
		if(0!=Configure.instance().PROXY_WORKER_THREADS){
			ThreadPoolConfiguration threadPoolConfiguration= new ThreadPoolConfiguration();
			threadPoolConfiguration.withProxyToServerWorkerThreads(Configure.instance().PROXY_WORKER_THREADS);
			threadPoolConfiguration.withClientToProxyWorkerThreads(Configure.instance().PROXY_WORKER_THREADS);
			bootstrap.withThreadPoolConfiguration(threadPoolConfiguration);
		}
		bootstrap.withAddress(listenInetSocketAddress);
		bootstrap.withProxyAuthenticator(myProxyAuthenticator);
		bootstrap.withFiltersSource(new HttpFiltersSourceAdapter() {
			public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
				return new HttpFiltersAdapter(originalRequest) {
					@Override
					public HttpResponse clientToProxyRequest(HttpObject httpObject) {
						return null;
					}

					@Override
					public HttpObject serverToProxyResponse(HttpObject httpObject) {
						return httpObject;
					}
				};
			}
		});
		bootstrap.start();
	}

}
