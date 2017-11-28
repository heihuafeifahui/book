package com.book.util;

import java.util.HashMap;
import java.util.Map;

public class Result {

	public static Map<String, Object> toUrl(String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tourl", url);
		return map;
	}

	public static Map<String, Object> success() {
		return setResult("success");
	}

	public static Map<String, Object> failed() {
		return setResult("failed");
	}

	public static Map<String, Object> yes() {
		return setResult("yes");
	}

	public static Map<String, Object> no() {
		return setResult("no");
	}

	public static Map<String, Object> setResult(String result) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	public static Map<String, Object> set(String key, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		return map;
	}

}
