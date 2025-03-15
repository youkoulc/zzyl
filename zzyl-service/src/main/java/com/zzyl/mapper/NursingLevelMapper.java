package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingLevel;
import com.zzyl.vo.NursingLevelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description NursingLevelMapper
 * @Author HeFeng
 * @Date 2024-07-09
 */
@Mapper
public interface NursingLevelMapper {
    /**
     * 查询所有护理等级
     * @return
     */
    List<NursingLevelVo> selectAll();

    /**
     * 护理等级条件分页查询
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    Page<NursingLevelVo> selectByPage(String name, Integer status);

    /**
     * 新增护理等级
     * @param
     * @return
     */
    void insert(NursingLevel nursingLevel);

    /**
     * 根据id查询护理等级
     * @param id
     * @return
     */
    NursingLevelVo selectById(Long id);

    /**
     * 修改护理等级
     * @param nursingLevel
     * @return
     */
    void update(NursingLevel nursingLevel);

    /**
     * 删除护理等级
     * @param id
     * @return
     */
    void deleteById(Long id);
}
