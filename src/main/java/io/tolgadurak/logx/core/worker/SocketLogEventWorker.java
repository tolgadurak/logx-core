package io.tolgadurak.logx.core.worker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;

public class SocketLogEventWorker extends SocketWorker {
	private static final Logger logger = LogManager.getLogger(SocketStringWorker.class);

	public SocketLogEventWorker(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public LogEvent consumeStream(InputStream inputStream) throws IOException, ClassNotFoundException,
			InvalidClassException, StreamCorruptedException, OptionalDataException {
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		return (LogEvent) objectInputStream.readObject();
	}

	@Override
	public String echoResponse(OutputStream outputStream, String message) throws IOException {
		StringBuilder echoBuilder = new StringBuilder().append("HTTP/1.1 200 OK\n\n").append("requestDate: ")
				.append(incomingDate).append("\nresponseDate: ").append(new Date()).append("\nserverMessage:\n")
				.append(message);
		outputStream.write(echoBuilder.toString().getBytes());
		outputStream.flush();
		outputStream.close();
		return echoBuilder.toString();
	}

	@Override
	public LogEvent call() throws Exception {
		// TODO: Error handling
		// LogEvent logEvent = consumeStream(inputStream);
		logger.info("Finished working.");
		return null;
	}

}
