package net.aimeizi.mina.examples.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
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
//		cause.printStackTrace();
	}

	public void inputClosed(IoSession session) throws Exception {

	}

	/**
	 * 处理从服务端或控制台输入的消息
	 */
	public void messageReceived(IoSession session, Object message) throws Exception {
		
		if(message!=null){//接受服务端返回的消息
			Date date = new Date();
			String printmsg = "";
			if(message instanceof Message){
				Message msg = (Message) message;
				int command = msg.getCommand();
				switch (command) {
				case Command.QUIT://处理服务端返回的退出消息
					session.close(true);
					System.exit(0);//退出客户端
					break;
				default:
					System.out.println("服务器已抽风....");
					break;
				}
				printmsg = "服务端应答:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) +" "+msg.getMsgContent();
			}else{
				printmsg = "服务端应答:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) +" "+message.toString();
			}
			System.out.println(printmsg);
		}
		
		//从控制台接受输入
		Scanner scanner = new Scanner(System.in);
		if(scanner.hasNext()){
			String in = scanner.next();
			Message msg = new Message();
			msg.setId(session.getId());
			msg.setUser("admin");
			if(in.equalsIgnoreCase("quit")){
				msg.setMsgContent(in);//接受用户从控制台的输入
				msg.setCommand(Command.QUIT);
			}else{
				msg.setMsgContent(in);//接受用户从控制台的输入
				msg.setCommand(Command.BROADCAST);
			}
			session.write(msg);
		}
		
	}

	public void messageSent(IoSession session, Object message) throws Exception {
	}

	public void sessionClosed(IoSession session) throws Exception {

	}

	//创建会话
	public void sessionCreated(IoSession session) throws Exception {
		
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	//会话建立并打开时调用。第一次建立连接的时候
	public void sessionOpened(IoSession session) throws Exception {
		Message message = new Message();
		message.setId(session.getId());
		message.setUser("admin");
		message.setMsgContent("客户:"+message.getUser()+"已连接!");
		message.setCommand(Command.LOGIN);
		session.write(message);
	}

}
