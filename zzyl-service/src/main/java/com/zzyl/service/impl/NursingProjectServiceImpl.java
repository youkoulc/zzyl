package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageResponse<NursingProjectVo> getByPage(String name, Integer pageNum, Integer pageSize, Integer status) {
        // 开启分页插件
        PageHelper.startPage(pageNum, pageSize);
        // 查询
        Page<NursingProjectVo> page = nursingProjectMapper.selectByPage(name, status);
        // 转换成PageResponse
        PageResponse<NursingProjectVo> pageResponse = new PageResponse(page);
        // 设置返回数据
        pageResponse.setRecords(page.getResult());
        // 关闭分页插件
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

    /**
     * 根据id查询护理项目
     *
     * @param id
     * @return
     */
    @Override
    public NursingProjectVo getById(Long id) {
        NursingProject nursingProject = nursingProjectMapper.selectById(id);
        return BeanUtil.toBean(nursingProject, NursingProjectVo.class);
    }

    /**
     * 修改护理项目
     *
     * @param nursingProjectDto
     * @return
     */
    @Override
    public void update(NursingProjectDto nursingProjectDto) {
        nursingProjectMapper.update(BeanUtil.toBean(nursingProjectDto, NursingProject.class));
    }

    /**
     * 禁用与启用护理项目
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public void switchStatus(Long id, Integer status) {
        // 根据id获取护理项目实体
        NursingProject nursingProject = nursingProjectMapper.selectById(id);
        // 更新状态
        nursingProject.setStatus(status);
        // 更新该项目
        nursingProjectMapper.update(nursingProject);
    }

    /**
     * 根据id删除当前护理项目
     *
     * @param id
     * @return
     */
    @Override
    public void deleteById(Long id) {
        nursingProjectMapper.deleteById(id);
    }

    /**
     * 获取所有护理项目
     *
     * @return
     */
    @Override
    public List<NursingProjectVo> getNursingProject() {
        List<NursingProject> nursingProjects = nursingProjectMapper.selectAll();
        return nursingProjects.stream().map(item -> BeanUtil.toBean(item, NursingProjectVo.class)).collect(Collectors.toList());

    }
}
