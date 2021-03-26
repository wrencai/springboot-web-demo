package net.atesu.atesvcdataapi.util;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 */
class HttpUtil {
    /**
     * get请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
//    public static String get(String url, MultiValueMap<String, String> params) {
//        return get(url, params, null);
//    }


    public static String get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, params, null);
    }
    /**
     * get请求
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, String> params, Map<String,String> headers) {
        Map<String, List<String>> paramsMap = new HashMap<>();
        if(params != null) {
            params.forEach((key,value) -> {
                List<String> valueList = new ArrayList<>(1);
                valueList.add(value);
                paramsMap.put(key,valueList);
            });
        }
        Map<String, List<String>> headersMap = new HashMap<>();
        if(headers != null) {
            headers.forEach((key,value) -> {
                List<String> valueList = new ArrayList<>(1);
                valueList.add(value);
                headersMap.put(key,valueList);
            });
        }
        return request(url, new MultiValueMapAdapter<String,String>(paramsMap)
                , new MultiValueMapAdapter<String,String>(headersMap)
                , HttpMethod.GET);
    }

    public static String post(String url, Map<String, String> params, Map<String,String> headers) {
        Map<String, List<String>> paramsMap = new HashMap<>();
        if(params != null) {
            params.forEach((key,value) -> {
                List<String> valueList = new ArrayList<>(1);
                valueList.add(value);
                paramsMap.put(key,valueList);
            });
        }
        Map<String, List<String>> headersMap = new HashMap<>();
        if(headers != null) {
            headers.forEach((key,value) -> {
                List<String> valueList = new ArrayList<>(1);
                valueList.add(value);
                headersMap.put(key,valueList);
            });
        }
        return request(url, new MultiValueMapAdapter<String,String>(paramsMap)
                , new MultiValueMapAdapter<String,String>(headersMap)
                , HttpMethod.POST);
    }

//    /**
//     * get请求
//     *
//     * @param url
//     * @param params  请求参数
//     * @param headers 请求头
//     * @return
//     */
//    public static String get(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
//        return request(url, params, headers, HttpMethod.GET);
//    }

//    /**
//     * post请求
//     *
//     * @param url
//     * @param params 请求参数
//     * @return
//     */
//    public static String post(String url, MultiValueMap<String, String> params) {
//        return post(url, params, null);
//    }

//    /**
//     * post请求
//     *
//     * @param url
//     * @param params  请求参数
//     * @param headers 请求头
//     * @return
//     */
//    public static String post(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
//        return request(url, params, headers, HttpMethod.POST);
//    }

    /**
     * put请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String put(String url, MultiValueMap<String, String> params) {
        return put(url, params, null);
    }

    /**
     * put请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String put(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.PUT);
    }

    /**
     * delete请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String delete(String url, MultiValueMap<String, String> params) {
        return delete(url, params, null);
    }

    /**
     * delete请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String delete(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.DELETE);
    }

    /**
     * 表单请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @param method  请求方式
     * @return
     */
    public static String request(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers, HttpMethod method) {
        if (params == null) {
            params = new LinkedMultiValueMap<>();
        }
        return request(url, params, headers, method, MediaType.APPLICATION_FORM_URLENCODED);
    }

    /**
     * http请求
     *
     * @param url
     * @param params    请求参数
     * @param headers   请求头
     * @param method    请求方式
     * @param mediaType 参数类型
     * @return
     */
    public static String request(String url, Object params, MultiValueMap<String, String> headers, HttpMethod method, MediaType mediaType) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }
        RestTemplate client = new RestTemplate();
        // header
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            httpHeaders.addAll(headers);
        }
        // 提交方式：表单、json
        httpHeaders.setContentType(mediaType);
        HttpEntity<Object> httpEntity = new HttpEntity(params, httpHeaders);
        ResponseEntity<String> response = client.exchange(url, method, httpEntity, String.class);
        try {
            return new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }

    public static void main(String[] args) {
        String url = "https://www.baidu.com";
        Map<String,String> params = new HashMap<>();
        String res = HttpUtil.get(url, params);
        System.out.println(res);
    }
}