package net.aimeizi.mina.examples.tcp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import net.aimeizi.mina.examples.handler.HelloClientHandler;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 基于Tcp的Client
 * @author welcome
 *
 */
public class HelloTcpClient {

	private static final String HOSTNAME = "127.0.0.1";
	private static final int PORT = 9898;

	public static void main(String[] args) {
		NioSocketConnector connector = new NioSocketConnector(); //TCP Connector
		connector.getFilterChain().addLast("logging", new LoggingFilter());
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("gbk"))));
	    connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
		connector.setHandler(new HelloClientHandler());
	    IoSession session;

	    for (;;) {
	        try {
	            ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
	            future.awaitUninterruptibly();
	            session = future.getSession();
	            break;
	        } catch (RuntimeIoException e) {
	            System.err.println("Failed to connect.");
	            e.printStackTrace();
	        }
	    }
	    session.getCloseFuture().awaitUninterruptibly();
	    connector.dispose();
	}

}
