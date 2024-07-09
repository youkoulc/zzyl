package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.vo.NursingLevelVo;

import java.util.List;

/**
 * @Description NursingLevelService
 * @Author HeFeng
 * @Date 2024-07-09
 */
public interface NursingLevelService {
    /**
     * 查询所有护理等级
     * @return
     */
    List<NursingLevelVo> getAll();

    /**
     * 护理等级条件分页查询
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    PageResponse<NursingLevelVo> getByPage(String name, Integer pageNum, Integer pageSize, Integer status);

    /**
     * 新增护理等级
     * @param nursingLevelDto
     * @return
     */
    void add(NursingLevelDto nursingLevelDto);

    /**
     * 根据id查询护理等级
     * @param id
     * @return
     */
    NursingLevelVo getById(Integer id);

    /**
     * 修改护理等级
     * @param nursingLevelDto
     * @return
     */
    void update(NursingLevelDto nursingLevelDto);

    /**
     * 删除护理等级
     * @param id
     * @return
     */
    void delete(Integer id);

    /**
     * 切换状态
     * @param id
     * @param status
     * @return
     */
    void switchStatus(Integer id, Integer status);
}
