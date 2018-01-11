package com.common.httplibrary;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/01/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class OkHttpUtils {

    private static OkHttpClient okHttpClient;

    //请求的集合
    private static HashMap<String, Call> mCallHashMap = new HashMap<>();

    public static void get(String url, Callback callback) {
        get(url, callback, null);
    }

    public static void get(String url, Callback callback, Object tag) {
        Request.Builder requestBuild = getRequst();
        sendGetRequest(url, requestBuild, callback, tag);
    }

    public static void post(String url, Map<String, Object> map, Callback callback) {
        post(url, map, callback, null);
    }

    public static void post(String url, Map<String, Object> map, Callback callback, Object tag) {
        String para = mapToJson(map);
        sendPostRequest(url, para, callback, tag);
    }

    public static void post(String url, String requestJson, Callback callback) {
        post(url, requestJson, callback, null);
    }

    public static void post(String url, String requestJson, Callback callback, Object tag) {
        sendPostRequest(url, requestJson, callback, tag);
    }

    public static void post(String url, Callback callback) {
        post(url, callback, null);
    }

    public static void post(String url, Callback callback, Object tag) {
        sendPostRequest(url, "", callback, tag);
    }

    //-----------------------分割线-----------------------------

    private static void sendGetRequest(String url, Request.Builder requestBuild, Callback callback, Object tag) {
        requestBuild.url(url);
        if (tag != null) {
            requestBuild.tag(tag);
        } else {
            requestBuild.tag(url);
        }
        requestBuild.get();
        OkHttpClient client = getHttpClient();
        try {
            Call call = client.newCall(requestBuild.build());
            call.enqueue(callback);
            //设置取消任务标签
            mCallHashMap.put(url, call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendPostRequest(String url, String requestContent, Callback callback, Object tag) {
        Request.Builder requestBuild = getRequst();
        requestBuild.url(url);
        if (tag != null) {
            requestBuild.tag(tag);
        } else {
            requestBuild.tag(url);
        }
        if (null == requestContent) {
            requestContent = "";
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestContent);
        requestBuild.post(requestBody);
        OkHttpClient client = getHttpClient();
        try {
            Call call = client.newCall(requestBuild.build());
            call.enqueue(callback);
            //设置取消任务标签
            mCallHashMap.put(url, call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-----------------------分割线-----------------------------

    public static Request.Builder getRequst() {
        Request.Builder request = new Request.Builder()
                .header("content-type", "application/json")
                .addHeader("platform", "android");
        return request;
    }

    //-----------------------分割线-----------------------------

    public static OkHttpClient getHttpClient() {
        if (okHttpClient == null) {
            //默认信任所有的证书
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate[] x509Certificates = new X509Certificate[0];
                    return x509Certificates;
                }
            };
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(15000, TimeUnit.MILLISECONDS);
            builder.readTimeout(15000, TimeUnit.MILLISECONDS);
            builder.writeTimeout(15000, TimeUnit.MILLISECONDS);
            builder.addInterceptor(new ChuckInterceptor(HttpManager.getApp()));
            builder.sslSocketFactory(sslSocketFactory, trustManager);
            builder.hostnameVerifier(DO_NOT_VERIFY);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    //-----------------------分割线-----------------------------

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    //-----------------------分割线-----------------------------


    public static void uploadFile(List<String> paths, String url, Callback callback) {
        Request.Builder requestBuild = getRequst();
        requestBuild.url(url);
        RequestBody requestBody = buildMultipartFormRequestBody(paths);
        requestBuild.post(requestBody);
        OkHttpClient client = getHttpClient();
        requestBuild.url(url);
        try {
            Call call = client.newCall(requestBuild.build());
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void uploadFile(List<String> paths, HashMap<String, Object> paramsMap, String url, Callback callback) {
        Request.Builder requestBuild = getRequst();
        requestBuild.url(url);
        RequestBody requestBody = buildMultipartFormRequestBody(paths, paramsMap);
        requestBuild.post(requestBody);
        OkHttpClient client = getHttpClient();
        requestBuild.url(url);
        try {
            Call call = client.newCall(requestBuild.build());
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static RequestBody buildMultipartFormRequestBody(List<String> paths) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String path = paths.get(i);
                if (!TextUtils.isEmpty(path)) {
                    File file = new File(path);
                    builder.addFormDataPart("upload" + i, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                }
            }
        }
        return builder.build();
    }

    private static RequestBody buildMultipartFormRequestBody(List<String> paths, HashMap<String, Object> paramsMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String path = paths.get(i);
                if (!TextUtils.isEmpty(path)) {
                    File file = new File(path);
                    builder.addFormDataPart("upload" + i, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                }
            }
        }
        if (paramsMap != null && paramsMap.size() > 0) {
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                }
            }
        }
        return builder.build();
    }


    //-----------------------分割线-----------------------------

    public static void cancle(Object tag) {
        try {
            if (mCallHashMap != null && mCallHashMap.size() > 0) {
                //获取KEY的集合
                Set<Map.Entry<String, Call>> keyEntries = mCallHashMap.entrySet();
                //如果包含KEY
                if (keyEntries.contains(tag)) {
                    //获取对应的Call
                    Call call = mCallHashMap.get(tag);
                    if (call != null) {
                        //如果没有被取消 执行取消的方法
                        if (!call.isCanceled()) {
                            call.cancel();
                        }
                        //移除对应的KEY
                        mCallHashMap.remove(tag);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

}
