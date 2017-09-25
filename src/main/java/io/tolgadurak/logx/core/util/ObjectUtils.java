package io.tolgadurak.logx.core.util;

public class ObjectUtils {
	public static <T> T getValueOrDefault(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}

	public static String toStringOrDefault(Object value, String defaultValue) {
		return value == null ? defaultValue : value.toString();
	}
}
