package org.jboss.netty.channel.socket.nio;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.net.Socket;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;


public class LoggingSocketChannel {
	
    static final InternalLogger logger =
            InternalLoggerFactory.getInstance(LoggingSocketChannel.class);
    
	private SocketChannel socket;
	
	public LoggingSocketChannel(SocketChannel socket) {
		this.socket = socket;
	}

	public Socket socket() {
		return socket.socket();
	}
	
	public boolean connect(SocketAddress remoteAddress) throws IOException {
		boolean success = socket.connect(remoteAddress);
		if (!success)
			return success;
		
        String strace = "";
        for (StackTraceElement ste : Thread.currentThread().getStackTrace())
        	strace += (" " + ste);
        logger.info("LoggingSocketChannel Connected: " + remoteAddress + 
        		" due to stack:" + strace);
        return success;
	}

	public boolean finishConnect() throws IOException {
		return socket.finishConnect();
	}

	public void register(Selector selector, int opConnect,
			Object att) throws ClosedChannelException {
		socket.register(selector, opConnect, att);
	}
	
	public void register(Selector selector, int opConnect) throws ClosedChannelException {
		socket.register(selector, opConnect);
	}
	
	public void close() throws IOException {
		socket.close();
	}
	
	public SocketChannel getRealSocketChannel() {
		return socket;
	}

	public SelectionKey keyFor(Selector selector) {
		return socket.keyFor(selector);
	}

	public void configureBlocking(boolean b) throws IOException {
		socket.configureBlocking(b);
	}

	
}
