package com.zzyl.mapper;

import com.zzyl.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<UserRole> list);
    // 根据角色id查找有无关联用户
    @Select("select distinct user_id from zzyl.sys_user_role where role_id=#{roleId}")
    List<Long> selectByRoleId(Long roleId);

    @Delete("delete from zzyl.sys_user_role where user_id = #{userId}")
    void deleteByUserId(Long userId);
}