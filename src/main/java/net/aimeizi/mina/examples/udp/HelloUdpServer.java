package net.aimeizi.mina.examples.udp;

import java.net.InetSocketAddress;

import net.aimeizi.mina.examples.handler.HelloServerHandler;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

/**
 * 基于Udp的服务端
 * @author welcome
 *
 */
public class HelloUdpServer {

private static final int PORT = 9898;

	public static void main(String[] args) throws Exception {
		NioDatagramAcceptor acceptor = new NioDatagramAcceptor();//UDP Acceptor
		acceptor.getFilterChain().addLast("logging", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		acceptor.getFilterChain().addLast("mdc", new MdcInjectionFilter());
		acceptor.setHandler(new HelloServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		DatagramSessionConfig dcfg = acceptor.getSessionConfig();
		dcfg.setReuseAddress(true);
		acceptor.bind(new InetSocketAddress(PORT));
	}

}
