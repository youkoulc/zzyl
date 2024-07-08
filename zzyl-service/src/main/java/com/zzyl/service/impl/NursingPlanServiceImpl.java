package com.zzyl.service.impl;

import com.zzyl.entity.NursingPlan;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NursingPlanServiceImpl implements NursingPlanService {
    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    @Override
    public List<NursingPlanVo> getNursingPlan() {
        List<NursingPlan> nursingPlans = nursingProjectMapper.getNursingPlan();

        return nursingPlans.stream().map(nursingPlan -> {
            NursingPlanVo nursingPlanVo = new NursingPlanVo();
            BeanUtils.copyProperties(nursingPlan, nursingPlanVo);
            return nursingPlanVo;
        }).collect(Collectors.toList());
    }
}
