package net.aimeizi.mina.examples.handler;

import java.util.Scanner;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 客户端消息处理类
 * @author welcome
 *
 */
public class HelloClientHandler implements IoHandler {

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	public void inputClosed(IoSession session) throws Exception {

	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		//从控制台接受输入
		Scanner scanner = new Scanner(System.in);
		if(scanner.hasNext()){
			session.write(scanner.next());
		}
	}

	public void messageSent(IoSession session, Object message) throws Exception {
//		System.out.println("客户端消息已发送!");
	}

	public void sessionClosed(IoSession session) throws Exception {

	}

	//创建会话
	public void sessionCreated(IoSession session) throws Exception {
		
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//		System.out.println( "IDLE " + session.getIdleCount( status ));
	}

	//会话建立并打开时调用。第一次建立连接的时候
	public void sessionOpened(IoSession session) throws Exception {
		String user = "admin";
		session.write("客户: " + user + " 已连接!");
	}

}
