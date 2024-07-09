package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.dto.NursingProjectPlanDto;
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

    /**
     * 新增护理计划
     * @param nursingPlanDto
     * @return
     */
    void addNursingPlan(NursingPlanDto nursingPlanDto);

    /**
     * 删除护理计划
     * @param id
     */
    void deleteNursingPlan(Integer id);

    /**
     * 根据id获取护理计划
     * @param id
     * @return
     */
    NursingPlanVo getById(Integer id);

    void updateNursingPlan(NursingPlanDto nursingPlanDto);
}
