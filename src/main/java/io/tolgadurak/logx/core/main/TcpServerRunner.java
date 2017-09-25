package io.tolgadurak.logx.core.main;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.tolgadurak.logx.core.server.TcpServer;

public class TcpServerRunner {

	private static final Logger logger = LogManager.getLogger(TcpServerRunner.class);

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		try {
			TcpServer server = new TcpServer(8090, Integer.MAX_VALUE, InetAddress.getByName("localhost"));
			executorService.submit(server);
		} catch (IOException e) {
			logger.error("General error", e);
		}
	}

}
