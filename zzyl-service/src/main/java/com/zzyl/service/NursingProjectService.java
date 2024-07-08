package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.vo.NursingProjectVo;

/**
 * @Description NursingProjectService
 * @Author HeFeng
 * @Date 2024-07-05
 */
public interface NursingProjectService {

    /**
     * 分页查询护理项目列表
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    PageResponse<NursingProjectVo> getByPage(String name, Integer pageNum, Integer pageSize, Integer status);

    /**
     * 新增护理项目
     * @param nursingProjectDto
     * @return
     */
    void add(NursingProjectDto nursingProjectDto);

    /**
     * 根据id查询护理项目
     * @param id
     * @return
     */
    NursingProjectVo getById(Long id);

    /**
     * 修改护理项目
     * @param nursingProjectDto
     * @return
     */
    void update(NursingProjectDto nursingProjectDto);

    /**
     * 禁用与启用护理项目
     * @param id
     * @param status
     * @return
     */
    void switchStatus(Long id, Integer status);
}
