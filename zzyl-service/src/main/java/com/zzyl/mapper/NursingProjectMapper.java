package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.vo.NursingProjectVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description NursingProjectMapper
 * @Author HeFeng
 * @Date 2024-07-05
 */
@Mapper
public interface NursingProjectMapper {
    Page<NursingProjectVo> selectByPage(String name, Integer status);
}
