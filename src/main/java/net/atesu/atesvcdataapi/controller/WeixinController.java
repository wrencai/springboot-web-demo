package net.atesu.atesvcdataapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.atesu.atesvcdataapi.constant.SessionConstant;
import net.atesu.atesvcdataapi.model.VO.ResultVO;
import net.atesu.atesvcdataapi.model.VO.SubscribeMessageSendForm;
import net.atesu.atesvcdataapi.util.HttpUtil;
import net.atesu.atesvcdataapi.util.weixin.WXCore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序服务端接口调用
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController extends AbstractController {
    @Value("${minapp.api.auth.code2Session}")
    private String URL_AUTH2SESSION;
    @Value("${minapp.api.auth.getAccessToken}")
    private String URL_GETACCESSTOKEN;
    @Value("${minapp.api.subscribeMessage.send}")
    private String URL_SUBSCRIBEMESSAGE_SEND;

    @Value("${minapp.appid}")
    private String minAppAppId;
    @Value("${minapp.secret}")
    private String minAppSecret;

    @GetMapping("/auth2session")
    public ResultVO<JSONObject> auth2session(@RequestParam String jscode , HttpServletRequest request) {
        logger.info("请求参数： jscode: {}, appId: {}, secret: {}", jscode, minAppAppId, minAppSecret);
        Map<String,String> params = new HashMap<>();
        params.put("appid", minAppAppId);
        params.put("secret", minAppSecret);
        params.put("js_code", jscode);
        params.put("grant_type", "authorization_code");
        String reponseDataStr = HttpUtil.doGet(URL_AUTH2SESSION, params);
        JSONObject json = JSON.parseObject(reponseDataStr);
        logger.info("响应： {}", json.toJSONString());
        request.getSession().setAttribute(SessionConstant.WEIXIN_SESSION_KEY, json.get("session_key"));
        // todo- session-key不能给前台
        json.remove("session_key");
        return ResultVO.success(json);
    }

    /**
     * 获取微信小程序的session_key信息
     * @return
     */
    private String getWeixinSessionKey() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return (String)request.getSession().getAttribute(SessionConstant.WEIXIN_SESSION_KEY);
    }

    @GetMapping("/getAccessToken")
    public ResultVO<JSONObject> getAccessToken() {
        logger.info("请求参数： appId: {}, secret: {}", minAppAppId, minAppSecret);
        Map<String,String> params = new HashMap<>();
        params.put("appid", minAppAppId);
        params.put("secret", minAppSecret);
        params.put("grant_type", "client_credential");
        String reponseDataStr = HttpUtil.doGet(URL_GETACCESSTOKEN, params);
        JSONObject json = JSON.parseObject(reponseDataStr);
        logger.info("响应： {}", json.toJSONString());
        return ResultVO.success(json);
    }

    @RequestMapping("/decrypt")
    public ResultVO<JSONObject> decrypt(@RequestParam String encryptedData, @RequestParam String iv) {
        logger.info("请求参数： appId: {}, secret: {}, encryptedData: {}, iv: {}", minAppAppId, minAppSecret, encryptedData, iv);
        JSONObject json = null;
        try{
            String decryptData = WXCore.decrypt(minAppAppId,encryptedData, getWeixinSessionKey(), iv);
            logger.info("响应： {}", decryptData);
            json = JSON.parseObject(decryptData);
            if(json == null) {
                return ResultVO.failed("解密结果为空，解密失败",null);
            }else {
                return ResultVO.success(json);
            }
        } catch (Exception e) {
            logger.error("微信小程序数据解密失败", e);
            return ResultVO.failed(e.getMessage(),null);
        }
    }


    @PostMapping("/subscribeMessageSend")
    public ResultVO<JSONObject> subscribeMessageSend(@RequestBody SubscribeMessageSendForm form) {
        logger.info("请求参数： SubscribeMessageSendForm: {}", JSON.toJSONString(form));
        JSONObject params = new JSONObject();
//        params.put("access_token", form.getAccessToken());
        params.put("touser", form.getTouser());
        params.put("template_id", form.getTemplateId());
        params.put("page", form.getPage());
        params.put("data", form.getData());
        params.put("miniprogram_state", form.getMiniprogramState());
        params.put("lang", form.getLang());
        String url = String.format(URL_SUBSCRIBEMESSAGE_SEND, form.getAccessToken());
        String reponseDataStr = HttpUtil.doPostJson(url, params.toJSONString());
        JSONObject json = JSON.parseObject(reponseDataStr);
        logger.info("响应： {}", json.toJSONString());
        return ResultVO.success(json);
    }

//    @GetMapping("/subscribeMessageSend")
//    public ResultVO<JSONObject> subscribeMessageSend(@RequestParam String accessToken, String touser, String templateId
//        String page, String data) {
//        logger.info("请求参数： jscode: {}, appId: {}, secret: {}", jscode, minappAppId, minAppSecret);
//        Map<String,String> params = new HashMap<>();
//        params.put("access_token",accessToken);
//        params.put("touser", minAppSecret);
//        params.put("js_code", jscode);
//        params.put("grant_type", "authorization_code");
//        String reponseDataStr = HttpUtil.doPost(URL_SUBSCRIBEMESSAGE_SEND, params);
//        JSONObject json = JSON.parseObject(reponseDataStr);
//        logger.info("响应： {}", json.toJSONString());
//        return ResultVO.success(json);
//    }

}
