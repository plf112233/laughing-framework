package com.laughing.lang.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.laughing.lang.LaughingException;

public class UrlUtil {

    public static final String HTTPS = "https";

    public static final String HTTP = "http";

    public static final int DEFAULT_HTTPS_PORT = 443;

    public static final int DEFAULT_HTTP_PORT = 80;


    public static String encode(String name, String charset) {
		try {
			if (StringUtil.isEmpty(name)) {
				return name;
			}
			return URLEncoder.encode(name, charset);
		} catch (UnsupportedEncodingException e) {
			throw new LaughingException(e);
		}
	}
	
	public static String decode(String name, String charset) {
		try {
			if (StringUtil.isEmpty(name)) {
				return name;
			}
			return URLDecoder.decode(name, charset);
		} catch (UnsupportedEncodingException e) {
			throw new LaughingException(e);
		}
	}
	
	public static String getRequestURL(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();
		if (!StringUtil.isEmpty(queryString)) {
			requestURL.append("?");
			requestURL.append(queryString);
		}
		return requestURL.toString();
	}

}
