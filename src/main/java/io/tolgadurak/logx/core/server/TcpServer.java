package io.tolgadurak.logx.core.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

public class TcpServer extends AbstractTcpServer {

	int port;

	public TcpServer(int port, int backlog, InetAddress bindAddr) throws IOException {
		super(port, backlog, bindAddr, Integer.MAX_VALUE);
		this.port = port;
	}

	@Override
	public void run() {
		while (true) {
			try {
				handleConnection();
			} catch (IOException | InterruptedException | ExecutionException e) {
				logger.error("Unexpected error while listening port " + port, e);
			}
		}
	}

}
