package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingPlan;
import com.zzyl.entity.NursingProjectPlan;
import com.zzyl.vo.NursingPlanVo;
import com.zzyl.vo.NursingProjectPlanVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NursingPlanMapper {
    /**
     * 获取所有护理计划
     * @return
     */
    List<NursingPlan> getNursingPlan();

    Page<NursingPlanVo> selectByPage(String name, Integer status);

    void addNursingPlan(NursingPlan nursingPlan);
    void addNursingProjectPlan(NursingProjectPlan nursingProjectPlan);

    /**
     * 根据id删除护理计划
     * @param id
     */
    void deleteNursingPlan(Long id);
    void deleteNursingProjectPlan(Long id);

    /**
     * 根据id获取护理计划
     * @param
     * @return
     */
    NursingPlan getById(Long id);
    List<NursingProjectPlanVo> getProjectPlan(Long id);

    /**
     *  更新护理计划
     * @param nursingPlan
     */
    void updateNursingPlan(NursingPlan nursingPlan);

    NursingPlan selectById(Long id);
}
