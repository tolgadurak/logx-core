package io.tolgadurak.logx.core.factory;

import java.io.IOException;
import java.net.Socket;

import io.tolgadurak.logx.core.worker.Intent;
import io.tolgadurak.logx.core.worker.SocketLogEventWorker;
import io.tolgadurak.logx.core.worker.SocketStringWorker;
import io.tolgadurak.logx.core.worker.Worker;

public final class WorkerFactory {
	private static WorkerFactory factory;

	private WorkerFactory() {
	}

	public static final synchronized WorkerFactory getFactory() {
		if (factory == null) {
			factory = new WorkerFactory();
		}
		return factory;
	}

	public Worker get(Intent intent, Socket socket) throws IOException {
		switch (intent.getType()) {
		case STRING:
			return new SocketStringWorker(socket);
		case LOG_EVENT:
			return new SocketLogEventWorker(socket);
		default:
			throw new IllegalArgumentException("Intent is not supported");
		}
	}
}
