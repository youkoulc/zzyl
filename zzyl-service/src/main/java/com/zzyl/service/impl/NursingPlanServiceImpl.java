package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.entity.NursingPlan;
import com.zzyl.entity.NursingProject;
import com.zzyl.entity.NursingProjectPlan;
import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import com.zzyl.vo.NursingProjectPlanVo;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 新增护理计划
     *
     * @param nursingPlanDto
     * @return
     */
    @Override
    @Transactional
    public void addNursingPlan(NursingPlanDto nursingPlanDto) {
        NursingPlan nursingPlan = BeanUtil.toBean(nursingPlanDto, NursingPlan.class);
        nursingPlan.setStatus(1);
        nursingPlanMapper.addNursingPlan(nursingPlan);

        List<NursingProjectPlanDto> projectPlanDtos = nursingPlanDto.getProjectPlans();
        if (projectPlanDtos != null && !projectPlanDtos.isEmpty()) {
            for (NursingProjectPlanDto projectPlanDto : projectPlanDtos) {
                NursingProjectPlan nursingProjectPlan = BeanUtil.toBean(projectPlanDto, NursingProjectPlan.class);
                nursingProjectPlan.setPlanId(nursingPlan.getId());
                nursingPlanMapper.addNursingProjectPlan(nursingProjectPlan);
            }

        }


    }

    /**
     * 删除护理计划
     * @param id
     */
    @Override
    public void deleteNursingPlan(Integer id) {
        nursingPlanMapper.deleteNursingPlan(id);
        nursingPlanMapper.deleteNursingProjectPlan(id);
    }
}
