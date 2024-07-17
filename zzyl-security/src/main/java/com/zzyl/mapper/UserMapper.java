package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<User> list);

    /**
     * 用户分页
     *
     * @return
     */
    Page<User> getByPage(UserDto userDto);

    @Select("select * from sys_user")
    List<User> selectList();

    /**
     * 当前用户
     *
     * @return
     */
    UserVo selectCurrentUser(Long userId);
}