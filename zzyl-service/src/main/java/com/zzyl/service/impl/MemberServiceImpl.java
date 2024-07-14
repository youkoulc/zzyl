package com.zzyl.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zzyl.constant.Constants;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.entity.Member;
import com.zzyl.mapper.MemberMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.service.MemberService;
import com.zzyl.service.WechatService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.StringUtils;
import com.zzyl.vo.LoginVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description MemberServiceImpl
 * @Author HeFeng
 * @Date 2024-07-11
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final WechatService wechatService;
    private final JwtTokenManagerProperties jwtTokenManagerProperties;
    static List<String> DEFAULT_NICKNAME_PREFIX = Arrays.asList(
            "生活更美好",
            "大桔大利",
            "日富一日",
            "好柿开花",
            "柿柿如意",
            "一椰暴富",
            "大柚所为",
            "杨梅吐气",
            "天生荔枝"
    );

    /**
     * 小程序微信登录
     *
     * @param userLoginRequestDto
     * @return
     */
    @Override
    public LoginVo login(UserLoginRequestDto userLoginRequestDto) {
        // 根据 临时凭证获取openId
        String openId = wechatService.getOpenId(userLoginRequestDto.getCode());
        // 根据openId获取用户信息
        Member member = memberMapper.selectByOpenId(openId);

        // 如果客户为空，则创建用户，赋值openId
        if (ObjectUtil.isEmpty(member)) {
            // member = new Member();
            // member = Member.builder().openId(openId).build();
            // member.setOpenId(openId);
        }

        // 远程调用接口 获取手机号
        String phone = wechatService.getPhone(userLoginRequestDto.getPhoneCode());
        // 封装手机号到 member
        if (StrUtil.isEmpty(member.getPhone()) || !phone.equals(member.getPhone())) {
            member.setPhone(phone);
        }


        // 如果没找到客户，则后续新增用户信息，否则更新
        if (ObjectUtil.isNotEmpty(member.getId())) {
            // 有该用户，修改
            memberMapper.update(member);
        } else {


            // 随机生成昵称
            String nickName = DEFAULT_NICKNAME_PREFIX.get((int) (Math.random() * DEFAULT_NICKNAME_PREFIX.size())) + StringUtils.substring(member.getPhone(), 7);
            // 保存昵称到成员信息
            member.setName(nickName);
            memberMapper.insert(member);
        }

        // 返回登录对象，封装昵称和token
        LoginVo loginVo = new LoginVo();
        loginVo.setNickName(member.getName());

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.JWT_USERID, member.getId());
        String token = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), claims);
        loginVo.setToken(token);

        return loginVo;
    }
}
