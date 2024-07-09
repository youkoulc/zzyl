package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingPlan;
import com.zzyl.vo.NursingPlanVo;
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
}
