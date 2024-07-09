package com.zzyl.mapper;

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
}
