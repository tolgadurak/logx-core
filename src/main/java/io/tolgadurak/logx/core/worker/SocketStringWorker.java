package io.tolgadurak.logx.core.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketStringWorker extends SocketWorker {

	private static final Logger logger = LogManager.getLogger(SocketStringWorker.class);

	public SocketStringWorker(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public String consumeStream(InputStream inputStream) throws IOException {
		StringBuilder message = new StringBuilder();
		Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = null;
		while (bufferedReader.ready()) {
			line = bufferedReader.readLine();
			message.append(line);
			message.append("\n");
		}
		incomingDate = new Date();
		return message.toString();
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
	public String call() {
		String incoming = null;
		try {
			incoming = consumeStream(inputStream);
			echoResponse(outputStream, "It's OK");
		} catch (IOException e) {
			// TODO error handling
			logger.error("", e);
			return null;
		}
		logger.info("Finished working.");
		return incoming;
	}
}
