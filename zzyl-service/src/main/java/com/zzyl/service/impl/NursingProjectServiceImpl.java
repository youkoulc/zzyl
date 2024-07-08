package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description NursingProjectServiceImpl
 * @Author HeFeng
 * @Date 2024-07-05
 */
@Service
public class NursingProjectServiceImpl implements NursingProjectService {
    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    /**
     * 分页查询护理项目列表
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageResponse<NursingProjectVo> getByPage(String name, Integer pageNum, Integer pageSize, Integer status) {
        PageHelper.startPage(pageNum,pageSize);
        Page<NursingProjectVo> page=nursingProjectMapper.selectByPage(name,status);

        PageResponse<NursingProjectVo> pageResponse = new PageResponse(page);
        pageResponse.setRecords(page.getResult());
        return pageResponse;
    }

    /**
     * 新增护理项目
     *
     * @param nursingProjectDto
     * @return
     */
    @Override
    public void add(NursingProjectDto nursingProjectDto) {
        NursingProject nursingProject = BeanUtil.toBean(nursingProjectDto, NursingProject.class);
        nursingProjectMapper.insert(nursingProject);
    }
}
