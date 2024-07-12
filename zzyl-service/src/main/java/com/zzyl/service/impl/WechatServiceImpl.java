package com.zzyl.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.service.WechatService;
import com.zzyl.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Description WechatServiceImpl
 * @Author HeFeng
 * @Date 2024-07-12
 */
@Service
@Slf4j
public class WechatServiceImpl implements WechatService {
    // 登录
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    // 获取token
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    // 获取手机号
    private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

    @Value("${zzyl.wechat.appId}")
    private String appId;
    @Value("${zzyl.wechat.appSecret}")
    private String appSecret;

    /**
     * 根据临时登录凭证获取OpenId
     *
     * @param code
     * @return
     */
    @Override
    public String getOpenId(String code) {
        // 定义请求参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("js_code", code);

        // 远程调用获取openId
        String json = HttpUtil.get(REQUEST_URL, params);
        JSONObject jsonObject = JSONUtil.parseObj(json);

        // 如果返回errcode，说明有错误
        if (ObjectUtil.isNotEmpty(jsonObject.get("errcode"))) {
            log.error("获取openId失败：{}", jsonObject.get("errmsg"));
            throw new BaseException(BasicEnum.GET_OPENID_ERROR);
        }
        // 没有错误返回openId
        return jsonObject.get("openid").toString();
    }

    /**
     * 获取凭证token， 用于获取手机号使用
     *
     * @return
     */
    private String getAccessToken() {
        // 定义请求参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);

        String json = HttpUtil.get(TOKEN_URL, params);
        JSONObject jsonObject = JSONUtil.parseObj(json);

        // 如果返回errcode，说明有错误
        if (ObjectUtil.isNotEmpty(jsonObject.get("errcode"))) {
            log.error("获取access_token失败：{}", jsonObject.get("errmsg"));
            throw new BaseException(BasicEnum.GET_TOKEN_ERROR);
        }
        // 没有错误返回access_token
        return jsonObject.get("access_token").toString();
    }


    /**
     * 根据临时凭证获取手机号
     *
     * @param phoneCode
     * @return
     */
    @Override
    public String getPhone(String phoneCode) {
        // 远程调用获取access_token
        String accessToken = getAccessToken();


        // 定义请求参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", phoneCode);

        // 远程调用获取手机号
        String json = HttpUtil.post(PHONE_REQUEST_URL + accessToken, JSONUtil.toJsonStr(params));
        JSONObject jsonObject = JSONUtil.parseObj(json);

        // 如果返回errcode，说明有错误
        Object errcode = jsonObject.get("errcode");
        if (ObjectUtil.isNotEmpty(errcode) && Integer.valueOf(errcode.toString()) != 0) {
            log.error("获取phone失败：{}", jsonObject.get("errmsg"));
            throw new BaseException(BasicEnum.GET_PHONE_ERROR);
        }
        // 没有错误返回手机号
        return jsonObject.getJSONObject("phone_info").get("purePhoneNumber").toString();
    }
}
