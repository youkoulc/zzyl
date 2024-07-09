package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.entity.NursingPlan;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NursingPlanServiceImpl implements NursingPlanService {
    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    /**
     * 获取护理计划
     *
     * @return
     */
    @Override
    public List<NursingPlanVo> getNursingPlan() {
        List<NursingPlan> nursingPlans = nursingPlanMapper.getNursingPlan();
        return nursingPlans.stream().map(item -> BeanUtil.toBean(item, NursingPlanVo.class)).collect(Collectors.toList());

    }

    /**
     * 获取护理计划分页
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageResponse<NursingPlanVo> getByPage(String name, Integer pageNum, Integer pageSize, Integer status) {
        //
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingPlanVo> page = nursingPlanMapper.selectByPage(name, status);
        PageResponse<NursingPlanVo> pageResponse = new PageResponse(page);
        pageResponse.setRecords(page.getResult());

        return pageResponse;
    }
}
