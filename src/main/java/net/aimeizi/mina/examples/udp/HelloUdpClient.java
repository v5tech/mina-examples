package net.aimeizi.mina.examples.udp;

import java.net.InetSocketAddress;

import net.aimeizi.mina.examples.handler.HelloClientHandler;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

/**
 * 基于Udp客户端
 * @author welcome
 *
 */
public class HelloUdpClient {

	private static final String HOSTNAME = "127.0.0.1";
	private static final int PORT = 9898;
	
	public static void main(String[] args) {
		NioDatagramConnector connector = new NioDatagramConnector();//UDP Connector
		connector.getFilterChain().addLast("logging", new LoggingFilter());
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
		connector.setHandler(new HelloClientHandler());
	    ConnectFuture connFuture = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
	    for ( ; ; ) {
	    	try {
				connFuture.addListener(new IoFutureListener<IoFuture>() {
					IoSession session;
					public void operationComplete(IoFuture future) {
						ConnectFuture connFuture = (ConnectFuture)future;
						if(connFuture.isConnected()){
							session = future.getSession();
						} else {
				            System.out.println("Not connected...exiting");
				        }
					}
				});
			} catch (Exception e) {
				System.err.println("Failed to connect.");
				e.printStackTrace();
			}
		}
	}

}
