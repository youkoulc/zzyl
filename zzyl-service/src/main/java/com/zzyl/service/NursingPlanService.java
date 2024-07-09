package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.vo.NursingPlanVo;

import java.util.List;

public interface NursingPlanService {
    /**
     * 获取护理计划
     * @return
     */
    List<NursingPlanVo> getNursingPlan();

    /**
     *  分页获取护理计划
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    PageResponse<NursingPlanVo> getByPage(String name, Integer pageNum, Integer pageSize, Integer status);
}
