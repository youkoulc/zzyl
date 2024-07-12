package com.zzyl.mapper;

import com.zzyl.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description MemberMapper
 * @Author HeFeng
 * @Date 2024-07-11
 */
@Mapper
public interface MemberMapper {

    /**根据openId获取用户信息
     * @param OpenId
     * @return
     */
    @Select("select * from zzyl.member where open_id = #{openId}")
    Member selectByOpenId(String OpenId);

    public void insert(Member member);

    public void update(Member member);
}
