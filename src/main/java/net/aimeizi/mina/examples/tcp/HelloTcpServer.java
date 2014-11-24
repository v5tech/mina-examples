package net.aimeizi.mina.examples.tcp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import net.aimeizi.mina.examples.handler.HelloServerHandler;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 基于Tcp的服务端
 * @author welcome
 *
 */
public class HelloTcpServer {

	private static final int PORT = 9898;
	
	public static void main(String[] args) throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor(); //TCP Acceptor
		acceptor.getFilterChain().addLast("logging", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("gbk"))));
		acceptor.getFilterChain().addLast("mdc", new MdcInjectionFilter());
		acceptor.setHandler(new HelloServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(PORT));
	}

}
