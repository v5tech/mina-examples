package net.aimeizi.mina.examples.handler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.MdcInjectionFilter;

/**
 * 服务端消息处理类
 * @author welcome
 *
 */
public class HelloServerHandler implements IoHandler {

	//保存所有客户端已连接的会话
	private final Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());

	//保存已连接的客户端
    private final Set<String> users = Collections.synchronizedSet(new HashSet<String>());
	
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
//		cause.printStackTrace();
	}

	public void inputClosed(IoSession session) throws Exception {

	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		Message msg = (Message)message;
		int command = msg.getCommand();
		String user = msg.getUser();
		switch (command) {
		case Command.QUIT:
			session.write(msg);
			sessionClosed(session);
			break;
		case Command.BROADCAST:
			Date date = new Date();
			broadCast(user + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) +" "+msg.getMsgContent());//发送广播
			break;
		case Command.LOGIN:
			//保存当前会话
			sessions.add(session);
			session.setAttribute("user", user);
	        MdcInjectionFilter.setProperty(session, "user", user);
	        users.add(user);
	        broadCast("用户:"+user+"加入了会话!");//发送广播
			break;
		default:
			broadCast("阁下莫非来自外太空?地球很危险,快回火星去吧!");//发送广播
			break;
		}
	}

	public void messageSent(IoSession session, Object message) throws Exception {
	}

	public void sessionClosed(IoSession session) throws Exception {
		String user = (String) session.getAttribute("user");
        users.remove(user);//移除用户
        sessions.remove(session);//移除会话
        broadCast("用户" + user + "离开了会话.");
	}

	public void sessionCreated(IoSession session) throws Exception {

	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	public void sessionOpened(IoSession session) throws Exception {

	}
	
	/**
	 * 广播到所有的会话
	 * @param message
	 */
	public void broadCast(String message) {
        synchronized (sessions) {
            for (IoSession session : sessions) {
                if (session.isConnected()) {
                    session.write(message);
                }
            }
        }
    }

	/**
	 * 返回已连接的客户端总数
	 * @return
	 */
	public int getNumberOfUsers(){
		return users.size();
	}
	
	/**
	 * 把客户踢出会话
	 * @param client
	 */
	public void kick(String name) {
        synchronized (sessions) {
            for (IoSession session : sessions) {
                if (name.equals(session.getAttribute("user"))) {
//                	Message msg = new Message();
//        			msg.setId(session.getId());
//        			msg.setUser("admin");
//        			msg.setMsgContent("用户" + name + "被踢出会话.");
//    				msg.setCommand(Command.QUIT);
//    				session.write(msg);
                	broadCast("用户" + name + "被踢出会话.");
                    break;
                }
            }
        }
    }
}
