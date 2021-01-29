package pers.mine.scratchpad.util;

import java.nio.charset.Charset;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

public class WebKit {
	/**
	 * 默认ConnectTimeout
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 1000;
	/**
	 * 默认ReadTimeout
	 */
	public static final int DEFAULT_READ_TIMEOUT = 10 * 1000;
	/**
	 * 默认httpClient工厂:使用java.net.HttpURLConnection
	 */
	public final static SimpleClientHttpRequestFactory DEFAULT_HTTP_CLIENT_FACTORY;
	static {
		DEFAULT_HTTP_CLIENT_FACTORY = new SimpleClientHttpRequestFactory();
		DEFAULT_HTTP_CLIENT_FACTORY.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		DEFAULT_HTTP_CLIENT_FACTORY.setReadTimeout(DEFAULT_READ_TIMEOUT);
	}
	/**
	 * JSON类型
	 */
	public static MediaType JSON_MEDIA_TYPE = new MediaType("application", "json", Charset.forName("UTF-8"));

	public static ResponseEntity<JSONObject> getForJson(String url, Object... uriVariables) {
		return jsonHttpRequest(url, HttpMethod.GET, null, null, uriVariables);
	}

	public static ResponseEntity<JSONObject> getForJson(String url, JSONObject body, Object... uriVariables) {
		return jsonHttpRequest(url, HttpMethod.GET, body, null, uriVariables);
	}

	public static ResponseEntity<JSONObject> getForJson(String url, JSONObject body, HttpHeaders appendHeaders,
			Object... uriVariables) {
		return jsonHttpRequest(url, HttpMethod.GET, body, appendHeaders, uriVariables);
	}

	public static ResponseEntity<JSONObject> postForJson(String url, JSONObject body, HttpHeaders appendHeaders,
			Object... uriVariables) {
		return jsonHttpRequest(url, HttpMethod.POST, body, appendHeaders, uriVariables);
	}

	public static ResponseEntity<JSONObject> postForJson(String url, JSONObject body, Object... uriVariables) {
		return jsonHttpRequest(url, HttpMethod.POST, body, null, uriVariables);
	}

	public static ResponseEntity<JSONObject> jsonHttpRequest(String url, HttpMethod httpMethod, JSONObject body,
			HttpHeaders appendHeaders, Object... uriVariables) {
		RestTemplate restTemplate = new RestTemplate(DEFAULT_HTTP_CLIENT_FACTORY);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(JSON_MEDIA_TYPE);
		headers.setAccept(Collections.singletonList(JSON_MEDIA_TYPE));
		if (appendHeaders != null) {
			headers.putAll(appendHeaders);
		}
		HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(body, headers);

		ResponseEntity<JSONObject> response = restTemplate.exchange(url, httpMethod, requestEntity, JSONObject.class,
				uriVariables);
		return response;
	}

	public static void main(String[] args) {
		String url = "http://api.map.baidu.com/telematics/v3/weather?location=嘉兴&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
		ResponseEntity<JSONObject> result = getForJson(url);
		System.out.println(result.getStatusCode());

		System.out.println(result.getBody());
	}
}
