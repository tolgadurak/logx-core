package io.tolgadurak.logx.core.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.tolgadurak.logx.core.factory.WorkerFactory;
import io.tolgadurak.logx.core.worker.Intent;
import io.tolgadurak.logx.core.worker.Worker;
import io.tolgadurak.logx.core.worker.WorkerIntent;

public abstract class AbstractTcpServer extends ServerSocket implements Runnable {

	private ExecutorService executorService;
	private WorkerFactory workerFactory = WorkerFactory.getFactory();
	protected static final Logger logger = LogManager.getLogger(TcpServer.class);

	protected AbstractTcpServer(int port, int backlog, InetAddress bindAddr, int nSocketWorker) throws IOException {
		super(port, backlog, bindAddr);
		executorService = Executors.newFixedThreadPool(nSocketWorker);
	}

	protected void handleConnection() throws IOException, InterruptedException, ExecutionException {
		Socket clientSocket = accept();
		Intent intent = this.getIntent();
		Worker worker = workerFactory.get(intent, clientSocket);
		Future<Object> future = startWorker(worker);
		Object incomingLogEvent = future.get();
		logger.info(incomingLogEvent);
	}

	protected Intent getIntent() {
		return new WorkerIntent(Intent.Type.STRING);
	}

	/**
	 * Start asynchronous computation for SocketWorker and save the result in Future
	 * object
	 * 
	 * @param socket
	 *            the socket to be processed
	 * @return
	 * @throws IOException
	 */
	protected Future<Object> startWorker(Worker worker) throws IOException {
		return executorService.submit(worker);
	}
}
