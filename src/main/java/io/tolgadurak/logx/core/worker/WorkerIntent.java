package io.tolgadurak.logx.core.worker;

public class WorkerIntent implements Intent {
	private Intent.Type type;

	public WorkerIntent(Intent.Type type) {
		this.type = type;
	}

	@Override
	public Intent.Type getType() {
		return type;
	}

}
