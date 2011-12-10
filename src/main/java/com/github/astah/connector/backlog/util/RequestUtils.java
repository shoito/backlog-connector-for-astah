package com.github.astah.connector.backlog.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RequestUtils {
	public static final String UTF8 = "utf-8";

	public static String buildQueryParameters(Map<String, Object> params) {
		StringBuilder queryParameters = new StringBuilder();

		Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&");
			}

			Entry<String, Object> entity = iter.next();
			queryParameters.append(entity.getKey());
			queryParameters.append("=");
			
			Object value = entity.getValue();
			if (value instanceof String) {
				queryParameters.append("'");
				queryParameters.append(encode((String) value));
				queryParameters.append("'");
			} else {
				queryParameters.append(encode((String) value));
			}
		}
		return queryParameters.toString();
	}

	public static String encode(String value) {
		try {
			return URLEncoder.encode(value, UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
