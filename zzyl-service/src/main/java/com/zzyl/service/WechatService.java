package com.zzyl.service;

/**
 * @Description WechatService
 * @Author HeFeng
 * @Date 2024-07-11
 */
public interface WechatService {
    /**
     * 根据临时登录凭证获取OpenId
     * @param code
     * @return
     */
    public String getOpenId(String code);

    /**
     * 根据手机号临时凭证获取手机号
     * @param phoneCode
     * @return
     */
    public String getPhone(String phoneCode);


}
