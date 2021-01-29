package pers.mine.scratchpad.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * 基于 hutool  HttpRequest 的http工具类
 * @author Mine
 */
public final class HttpUtils {
	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
	/**
	 * GET
	 */
	public static final String HTTP_METHOD_GET = "GET";
	/**
	 * POST
	 */
	public static final String HTTP_METHOD_POST = "POST";

	/**
	 * 默认ConnectTimeout
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 1000;
	/**
	 * 默认ReadTimeout
	 */
	public static final int DEFAULT_READ_TIMEOUT = 10 * 1000;

	/**
	 * @see SimpleHttpUtils#jsonHttpRequest(java.lang.String, java.lang.String,
	 *      com.alibaba.fastjson.JSONObject, java.util.Map, java.net.Proxy)
	 */
	public static Resp getForJson(String url) throws Exception {
		return jsonHttpRequest(url, HTTP_METHOD_GET, null, null);
	}

	/**
	 * @see SimpleHttpUtils#jsonHttpRequest(java.lang.String, java.lang.String,
	 *      com.alibaba.fastjson.JSONObject, java.util.Map, java.net.Proxy)
	 */
	public static Resp getForJson(String url, JSONObject body) throws Exception {
		return jsonHttpRequest(url, HTTP_METHOD_GET, body, null);
	}

	/**
	 * @see SimpleHttpUtils#jsonHttpRequest(java.lang.String, java.lang.String,
	 *      com.alibaba.fastjson.JSONObject, java.util.Map, java.net.Proxy)
	 */
	public static Resp getForJson(String url, JSONObject body, Map<String, String> appendHeaders) throws Exception {
		return jsonHttpRequest(url, HTTP_METHOD_GET, body, appendHeaders);
	}

	/**
	 * @see SimpleHttpUtils#jsonHttpRequest(java.lang.String, java.lang.String,
	 *      com.alibaba.fastjson.JSONObject, java.util.Map, java.net.Proxy)
	 */
	public static Resp postForJson(String url, JSONObject body, Map<String, String> appendHeaders) throws Exception {
		return jsonHttpRequest(url, HTTP_METHOD_POST, body, appendHeaders);
	}

	/**
	 * @see SimpleHttpUtils#jsonHttpRequest(java.lang.String, java.lang.String,
	 *      com.alibaba.fastjson.JSONObject, java.util.Map, java.net.Proxy)
	 */
	public static Resp postForJson(String url, JSONObject body) throws Exception {
		return jsonHttpRequest(url, HTTP_METHOD_POST, body, null);
	}

	/**
	 * @see SimpleHttpUtils.jsonHttpRequest(java.lang.String, java.lang.String,
	 *      com.alibaba.fastjson.JSONObject, java.util.Map, java.net.Proxy)
	 */
	public static Resp jsonHttpRequest(String url, String httpMethod, JSONObject requestBody,
			Map<String, String> appendHeaders) throws Exception {
		return jsonHttpRequest(url, httpMethod, requestBody, null, null);
	}

	/**
	 * @param url           访问url，get请求需自己拼接url参数
	 * @param httpMethod    请求方法
	 * @param requestBody   请求体，JSON串
	 * @param appendHeaders 附加请求头
	 * @param proxy         设置请求代理
	 * @return 返回响应封装类Resp
	 * @throws Exception 未捕获任何异常，直接抛出
	 * @see Resp
	 */
	public static Resp jsonHttpRequest(String url, String httpMethod, JSONObject requestBody,
			Map<String, String> appendHeaders, Proxy proxy) throws Exception {
		HttpURLConnection conn = null;
		Resp resp = null;
		try {
			LOG.info("{} {}  proxy - {}", httpMethod, url, proxy);
			if (proxy != null) {
				conn = (HttpURLConnection) (new URL(url).openConnection(proxy));
			} else {
				conn = (HttpURLConnection) (new URL(url).openConnection());
			}
			conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
			conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
			conn.setRequestMethod(httpMethod);
			// 附加请求头
			if (appendHeaders != null) {
				for (Map.Entry<String, String> entry : appendHeaders.entrySet()) {
					conn.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			conn.setDoInput(true);
			String requstBody = null;
			if (requestBody != null && !requestBody.isEmpty()) {
				conn.setDoOutput(true);
				conn.addRequestProperty("Content-Type", "application/json;charset=utf-8");
				requstBody = requestBody.toJSONString();
			} else {
				conn.setDoOutput(false);
			}
			LOG.debug("Request Headers - {}", conn.getRequestProperties());
			LOG.debug("Request Body - {}", requstBody);

			conn.connect();

			// 传输请求体
			if (conn.getDoOutput()) {
				try (OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
					w.write(requstBody);
					w.flush();
				}
			}

			int httpStatus = conn.getResponseCode();
			LOG.info("Status Code - {}", httpStatus);
			Map<String, List<String>> respHeader = conn.getHeaderFields();
			LOG.debug("Response Headers - {}", respHeader);
			String responseBody = "";
			InputStream respInputStream = conn.getInputStream();
			if (respInputStream != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(respInputStream))) {
					StringBuffer sbu = new StringBuffer();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sbu.append(line);
					}
					responseBody = sbu.toString();
					LOG.debug("Response Body - {}", responseBody);
				}
			}
			String responseError = "";
			InputStream respErrorStream = conn.getErrorStream();
			if (respErrorStream != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(respErrorStream))) {
					StringBuffer sbu = new StringBuffer();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sbu.append(line);
					}
					responseError = sbu.toString();
				}
				LOG.debug("Response Error - {}", responseError);
			}
			// 封装响应
			resp = new Resp(httpStatus, respHeader, responseBody, responseError, true);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return resp;
	}

	/**
	 * 发送json请求，简化代理
	 *
	 * @param url           访问url，get请求需自己拼接url参数
	 * @param httpMethod    请求方法
	 * @param requestBody   请求体，JSON串
	 * @param appendHeaders 附加请求头
	 * @param proxyHost     代理host
	 * @param proxyPort     代理port
	 * @see SimpleHttpUtils#jsonHttpRequest(java.lang.String, java.lang.String,
	 *      com.alibaba.fastjson.JSONObject, java.util.Map, java.net.Proxy)
	 */
	public static Resp jsonHttpRequestWithProxy(String url, String httpMethod, JSONObject requestBody,
			Map<String, String> appendHeaders, String proxyHost, int proxyPort) throws Exception {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
		return jsonHttpRequest(url, httpMethod, requestBody, appendHeaders, proxy);
	}

	/**
	 * 简单的响应封装类
	 */
	public static class Resp {
		/**
		 * 状态码
		 */
		private final int statusCode;
		/**
		 * 错误信息，数据源于java.net.HttpURLConnection#getErrorStream()
		 */
		private final String error;
		/**
		 * 响应头
		 */
		private final Map<String, List<String>> headers;
		/**
		 * 响应体
		 */
		private final String body;
		/**
		 * 对json响应体的自动转换
		 */
		private JSONObject jsonBody;

		public int getStatusCode() {
			return statusCode;
		}

		public String getError() {
			return error;
		}

		public Map<String, List<String>> getHeaders() {
			return headers;
		}

		public String getBody() {
			return body;
		}

		public JSONObject getJsonBody() {
			return jsonBody;
		}

		public Resp(int statusCode, Map<String, List<String>> headers, String body, String error) {
			this.statusCode = statusCode;
			this.body = body;
			this.error = error;
			this.headers = headers;
		}

		public Resp(int statusCode, Map<String, List<String>> headers, String body, String error, boolean isJson) {
			this.statusCode = statusCode;
			this.error = error;
			this.body = body;
			this.headers = headers;
			if (isJson && statusCode > 199 && statusCode < 300) {
				try {
					this.jsonBody = JSONObject.parseObject(body);
				} catch (Exception e) {
					LOG.warn("body转json异常 - {}", e.getMessage());
				}
			}
		}

		@Override
		public String toString() {
			return MessageFormat.format(
					"Status Code - {0}\nResponse Headers - {1}\nResponse Body - {2}\nResponse Error - {3}", statusCode,
					headers, body, error);
		}
	}

	public static void main(String[] args) throws Exception {

		System.out.println(((String) null) instanceof String);
		System.out.flush();
		try {
			HttpResponse execute = HttpRequest.get("https://yebukong.com/minecms/api/appInfo")
					// .setConnectionTimeout(1)
					.setReadTimeout(1).execute();
			String body = execute.body();
			System.out.println(body);
		} catch (Exception e) {
			System.out.println("123 - " + e.getClass().getSimpleName());
			Throwable cause = e.getCause();
			System.out.println("123 - " + cause.getClass());
			e.printStackTrace();
			System.out.println(null instanceof Exception);
		}

	}
}
