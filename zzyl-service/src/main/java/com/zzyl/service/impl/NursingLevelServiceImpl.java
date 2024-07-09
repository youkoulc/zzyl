package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.entity.NursingLevel;
import com.zzyl.mapper.NursingLevelMapper;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description NursingLevelServiceImpl
 * @Author HeFeng
 * @Date 2024-07-09
 */
@Service
public class NursingLevelServiceImpl implements NursingLevelService {
    @Autowired
    private NursingLevelMapper nursingLevelMapper;

    /**
     * 查询所有护理等级
     *
     * @return
     */
    @Override
    public List<NursingLevelVo> getAll() {
        return nursingLevelMapper.selectAll();
    }

    /**
     * 护理等级条件分页查询
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageResponse<NursingLevelVo> getByPage(String name, Integer pageNum, Integer pageSize, Integer status) {
        PageHelper.startPage(pageNum,pageSize);
        Page<NursingLevelVo> page=nursingLevelMapper.selectByPage(name,status);
        PageResponse<NursingLevelVo> pageResponse = new PageResponse<>(page);
        pageResponse.setRecords(page.getResult());
        return pageResponse;
    }

    /**
     * 新增护理等级
     *
     * @param nursingLevelDto
     * @return
     */
    @Override
    public void add(NursingLevelDto nursingLevelDto) {
        NursingLevel nursingLevel = BeanUtil.toBean(nursingLevelDto, NursingLevel.class);
        nursingLevelMapper.insert(nursingLevel);
    }

    /**
     * 根据id查询护理等级
     *
     * @param id
     * @return
     */
    @Override
    public NursingLevelVo getById(Integer id) {
        return nursingLevelMapper.selectById(id);
    }

    /**
     * 修改护理等级
     *
     * @param nursingLevelDto
     * @return
     */
    @Override
    public void update(NursingLevelDto nursingLevelDto) {
        NursingLevel nursingLevel = BeanUtil.toBean(nursingLevelDto, NursingLevel.class);
        nursingLevelMapper.update(nursingLevel);
    }

    /**
     * 删除护理等级
     *
     * @param id
     * @return
     */
    @Override
    public void delete(Integer id) {
        nursingLevelMapper.deleteById(id);
    }
}
