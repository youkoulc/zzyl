package com.zzyl.mapper;

import com.zzyl.entity.NursingPlan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NursingPlanMapper {
    /**
     * 获取所有护理计划
     * @return
     */
    List<NursingPlan> getNursingPlan();
}
