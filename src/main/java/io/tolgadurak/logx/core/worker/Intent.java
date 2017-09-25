package io.tolgadurak.logx.core.worker;

public interface Intent {
	Intent.Type getType();

	public enum Type {
		STRING, LOG_EVENT
	}
}
