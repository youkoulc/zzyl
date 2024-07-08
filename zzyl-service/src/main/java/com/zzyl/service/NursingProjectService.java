package com.zzyl.service;

import com.zzyl.base.PageResponse;
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
}
