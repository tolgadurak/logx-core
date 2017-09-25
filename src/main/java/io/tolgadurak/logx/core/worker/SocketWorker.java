package io.tolgadurak.logx.core.worker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.Date;

public abstract class SocketWorker implements Worker {

	protected Date incomingDate;
	protected InputStream inputStream;
	protected OutputStream outputStream;

	public SocketWorker(Socket socket) throws IOException {
		this.inputStream = socket.getInputStream();
		this.outputStream = socket.getOutputStream();
	}

	protected abstract Object consumeStream(InputStream inputStream) throws IOException, ClassNotFoundException,
	InvalidClassException, StreamCorruptedException, OptionalDataException;

	protected abstract String echoResponse(OutputStream outputStream, String message) throws IOException;

}
