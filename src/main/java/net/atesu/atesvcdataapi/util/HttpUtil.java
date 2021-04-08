package net.atesu.atesvcdataapi.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static String doGet(String url) {							// 无参数get请求
        return doGet(url, null);
    }

    public static String doGet(String url, Map<String, String> param) {	// 带参数get请求
        CloseableHttpClient httpClient = HttpClients.createDefault();	// 创建一个默认可关闭的Httpclient 对象
        String resultMsg = "";											// 设置返回值
        CloseableHttpResponse response = null;							// 定义HttpResponse 对象
        try {
            URIBuilder builder = new URIBuilder(url);					// 创建URI,可以设置host，设置参数等
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);					// 创建http GET请求
//            httpGet.setHeader(key,value);                     //设置请求的请求头
            response = httpClient.execute(httpGet);						// 执行请求
            if (response.getStatusLine().getStatusCode() == 200) {		// 判断返回状态为200则给返回值赋值
                resultMsg = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {														// 不要忘记关闭
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }

    public static String doPost(String url) {							// 无参数post请求
        return doPost(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {// 带参数post请求
        CloseableHttpClient httpClient = HttpClients.createDefault();	// 创建一个默认可关闭的Httpclient 对象

        CloseableHttpResponse response = null;
        String resultMsg = "";
        try {
            HttpPost httpPost = new HttpPost(url);						// 创建Http Post请求
//            httpPost.setHeader(key,value);                            //设置post请求的请求头
            if (param != null) {										// 创建参数列表
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);// 模拟表单
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);					// 执行http请求
            if (response.getStatusLine().getStatusCode() == 200) {
                resultMsg = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }

    public static String doPostJson(String url, String json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
//            httpPost.setHeader(key,value);                            //设置post请求的请求头
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);     //指定传输参数为json
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}

//package net.atesu.atesvcdataapi.util;
//
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.MultiValueMapAdapter;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * http请求工具类
// */
//public class HttpUtil {
//    /**
//     * get请求
//     *
//     * @param url
//     * @param params 请求参数
//     * @return
//     */
////    public static String get(String url, MultiValueMap<String, String> params) {
////        return get(url, params, null);
////    }
//
//
//    public static String get(String url, Map<String, String> params) {
//        return get(url, params, null);
//    }
//
//    public static String post(String url, Map<String, String> params) {
//        return post(url, params, null);
//    }
//    /**
//     * get请求
//     * @param url
//     * @param params
//     * @return
//     */
//    public static String get(String url, Map<String, String> params, Map<String,String> headers) {
//        Map<String, List<String>> paramsMap = new HashMap<>();
//        if(params != null) {
//            params.forEach((key,value) -> {
//                List<String> valueList = new ArrayList<>(1);
//                valueList.add(value);
//                paramsMap.put(key,valueList);
//            });
//        }
//        Map<String, List<String>> headersMap = new HashMap<>();
//        if(headers != null) {
//            headers.forEach((key,value) -> {
//                List<String> valueList = new ArrayList<>(1);
//                valueList.add(value);
//                headersMap.put(key,valueList);
//            });
//        }
//        return request(url, new MultiValueMapAdapter<String,String>(paramsMap)
//                , new MultiValueMapAdapter<String,String>(headersMap)
//                , HttpMethod.GET);
//    }
//
//    public static String post(String url, Map<String, String> params, Map<String,String> headers) {
//        Map<String, List<String>> paramsMap = new HashMap<>();
//        if(params != null) {
//            params.forEach((key,value) -> {
//                List<String> valueList = new ArrayList<>(1);
//                valueList.add(value);
//                paramsMap.put(key,valueList);
//            });
//        }
//        Map<String, List<String>> headersMap = new HashMap<>();
//        if(headers != null) {
//            headers.forEach((key,value) -> {
//                List<String> valueList = new ArrayList<>(1);
//                valueList.add(value);
//                headersMap.put(key,valueList);
//            });
//        }
//        return request(url, new MultiValueMapAdapter<String,String>(paramsMap)
//                , new MultiValueMapAdapter<String,String>(headersMap)
//                , HttpMethod.POST);
//    }
//
////    /**
////     * get请求
////     *
////     * @param url
////     * @param params  请求参数
////     * @param headers 请求头
////     * @return
////     */
////    public static String get(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
////        return request(url, params, headers, HttpMethod.GET);
////    }
//
////    /**
////     * post请求
////     *
////     * @param url
////     * @param params 请求参数
////     * @return
////     */
////    public static String post(String url, MultiValueMap<String, String> params) {
////        return post(url, params, null);
////    }
//
////    /**
////     * post请求
////     *
////     * @param url
////     * @param params  请求参数
////     * @param headers 请求头
////     * @return
////     */
////    public static String post(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
////        return request(url, params, headers, HttpMethod.POST);
////    }
//
//    /**
//     * put请求
//     *
//     * @param url
//     * @param params 请求参数
//     * @return
//     */
//    public static String put(String url, MultiValueMap<String, String> params) {
//        return put(url, params, null);
//    }
//
//    /**
//     * put请求
//     *
//     * @param url
//     * @param params  请求参数
//     * @param headers 请求头
//     * @return
//     */
//    public static String put(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
//        return request(url, params, headers, HttpMethod.PUT);
//    }
//
//    /**
//     * delete请求
//     *
//     * @param url
//     * @param params 请求参数
//     * @return
//     */
//    public static String delete(String url, MultiValueMap<String, String> params) {
//        return delete(url, params, null);
//    }
//
//    /**
//     * delete请求
//     *
//     * @param url
//     * @param params  请求参数
//     * @param headers 请求头
//     * @return
//     */
//    public static String delete(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
//        return request(url, params, headers, HttpMethod.DELETE);
//    }
//
//    /**
//     * 表单请求
//     *
//     * @param url
//     * @param params  请求参数
//     * @param headers 请求头
//     * @param method  请求方式
//     * @return
//     */
//    public static String request(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers, HttpMethod method) {
//        if (params == null) {
//            params = new LinkedMultiValueMap<>();
//        }
//        return request(url, params, headers, method, MediaType.APPLICATION_FORM_URLENCODED);
//    }
//
//    /**
//     * http请求
//     *
//     * @param url
//     * @param params    请求参数
//     * @param headers   请求头
//     * @param method    请求方式
//     * @param mediaType 参数类型
//     * @return
//     */
//    public static String request(String url, Object params, MultiValueMap<String, String> headers, HttpMethod method, MediaType mediaType) {
//        if (url == null || url.trim().isEmpty()) {
//            return null;
//        }
//        RestTemplate client = new RestTemplate();
//        // header
//        HttpHeaders httpHeaders = new HttpHeaders();
//        if (headers != null) {
//            httpHeaders.addAll(headers);
//        }
//        // 提交方式：表单、json
//        httpHeaders.setContentType(mediaType);
//        HttpEntity<Object> httpEntity = new HttpEntity(params, httpHeaders);
//        ResponseEntity<String> response = client.exchange(url, method, httpEntity, String.class);
//        try {
//            return new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return response.getBody();
//    }
//
//    public static void main(String[] args) {
//        String url = "https://www.baidu.com";
//        Map<String,String> params = new HashMap<>();
//        String res = HttpUtil.get(url, params);
//        System.out.println(res);
//    }
//}