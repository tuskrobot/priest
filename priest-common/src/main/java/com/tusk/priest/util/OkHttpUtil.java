package com.tusk.priest.util;

import com.tusk.priest.enums.OkHttpMethodEnum;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by alvin on 2020/4/30 16:55
 **/
@Slf4j
public class OkHttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
    private static final MediaType FILE = MediaType.parse("multipart/form-data; charset=utf-8");

    private static final int CONNECTION_TIME_OUT = 3000;
    private static final int SOCKET_TIME_OUT = 3000;
    private static final int MAX_IDLE_CONNECTIONS = 30;
    private static final long KEEP_ALIVE_TIME = 10000L;

    private final OkHttpClient okHttpClient;

    private static volatile OkHttpUtil instance = null;

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) instance = new OkHttpUtil();
            }
        }
        return instance;
    }

    public OkHttpUtil() {
        ConnectionPool connectionPool = new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS);
        this.okHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(false)
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(new RetryInterceptor(2))
//                .addNetworkInterceptor(new NetworkInterceptor())
                .build();
    }

    /**
     * get 请求
     *
     * @param url 请求url地址
     * @return string
     */
    public String doGet(String url) {
        return doGet(url, null, null);
    }


    /**
     * get 请求
     *
     * @param url    请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public String doGet(String url, Map<String, Object> params) {
        return doGet(url, params, null);
    }

    /**
     * get 请求
     *
     * @param url     请求url地址
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     */
    public String doGet(String url, String[] headers) {
        return doGet(url, null, headers);
    }


    /**
     * get 请求
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头字段 {k1, v1, k2, v2, ...}
     * @return string
     */
    public String doGet(String url, Map<String, Object> params, String[] headers) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }

        Request.Builder builder = new Request.Builder();
        setHeaders(builder, headers);

        Request request = builder.url(sb.toString()).build();
        log.debug("do get request and url[{}]", sb);
        return execute(request);
    }


    /**
     * post 请求
     *
     * @param url    请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public String doPost(String url, Map<String, String> params) {
        return doPost(url, params, null);
    }

    public String doPost(String url, Map<String, String> params, String[] headers) {
        FormBody.Builder body = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                body.add(key, params.get(key));
            }
        }

        Request.Builder builder = new Request.Builder();
        setHeaders(builder, headers);

        Request request = builder.url(url).post(body.build()).build();

        return execute(request);
    }


    /**
     * post 请求, 请求数据为 json 的字符串
     *
     * @param url  请求url地址
     * @param json 请求数据, json 字符串
     * @return string
     */
    public String doPostJson(String url, String json) {
        return doPostJson(url, json, null);
    }

    public String doPostJson(String url, String json, String[] headers) {
        return executePost(url, json, headers, JSON);
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     *
     * @param url 请求url地址
     * @param xml 请求数据, xml 字符串
     * @return string
     */
    public String doPostXml(String url, String xml) {
        return doPostXml(url, xml, null);
    }

    public String doPostXml(String url, String xml, String[] headers) {
        return executePost(url, xml, headers, XML);
    }


    private String executePost(String url, String data, String[] headers, MediaType contentType) {
        log.info("do post url[{}] and request:{}", url, data);
        RequestBody body = RequestBody.create(contentType, data);
        Request.Builder builder = new Request.Builder();
        setHeaders(builder, headers);
        Request request = builder.url(url).post(body).build();

        return execute(request);
    }

    //返回值中添加header相应key的value
    private String execute(Request request, String[] headerKeys) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            String url = request.url().toString();
            log.info("do post url[{}] and request:{}", url, res);
            if (response.isSuccessful()) {
                StringBuilder headerValue = new StringBuilder();
                for (String headerKey : headerKeys) {
                    headerValue.append(response.header(headerKey)).append("\n");
                }
                return res + "\n" + headerValue;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    private String execute(Request request) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            String url = request.url().toString();
            log.info("do post url[{}] and request:{}", url, res);
            if (response.isSuccessful()) {
                return res;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
    /**
     * http 请求
     *
     * @param url 请求url地址
     * @param data 请求数据
     * @param file 请求升级文件
     * @param method 请求方法(put/post)
     * @return string
     */
    private String doHttpRequest(String url, String data, String[] headers, File file, int method, String[] headerKeys) {
        RequestBody body = StringUtils.isBlank(data) ? RequestBody.create(FILE, file) : RequestBody.create(JSON, data);
        Request.Builder builder = new Request.Builder();
        setHeaders(builder, headers);
        Request request;
        if (OkHttpMethodEnum.PUT.getValue() == method) {
            request = builder.url(url).put(body).build();
        } else if (OkHttpMethodEnum.POST.getValue() == method) {
            request = builder.url(url).post(body).build();
        } else {
            request = builder.url(url).build();
        }

        if (headerKeys == null) {
            return execute(request);
        }
        return execute(request, headerKeys);
    }



    public String doPostHeader(String url, String data, String[] headers, String[] headerKeys) {
        return doHttpRequest(url, data, headers, null, OkHttpMethodEnum.POST.getValue(), headerKeys);
    }

    public String doPostFile(String url, File file, String[] headers) {
        return doHttpRequest(url, "", headers, file, OkHttpMethodEnum.POST.getValue(), null);
    }

    public String doPutJson(String url, String data, String[] headers) {
        return doHttpRequest(url, data, headers, null, OkHttpMethodEnum.PUT.getValue(), null);
    }

    public String doPutFile(String url, File file, String[] headers) {
        return doHttpRequest(url, "", headers, file, OkHttpMethodEnum.PUT.getValue(), null);
    }

    private void setHeaders(Request.Builder builder, String[] headers) {
        if (headers != null && headers.length > 0) {
            if (headers.length % 2 == 0) {
                for (int i = 0; i < headers.length; i += 2) {
                    builder.addHeader(headers[i], headers[i + 1]);
                }
            } else {
                log.warn("headers's length[{}] is error.", headers.length);
            }
        }
    }
}
