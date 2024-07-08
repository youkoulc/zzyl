package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingProject;
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

    /**
     * 新增护理项目
     * @param nursingProject
     */
    void insert(NursingProject nursingProject);

    /**
     * 根据id查询护理项目
     *
     * @param id
     * @return
     */
    NursingProject selectById(Long id);

    /**
     * 修改护理项目
     * @param nursingProject
     * @return
     */
    void update(NursingProject nursingProject);
}
