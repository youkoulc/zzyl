package com.zzyl.service;

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
}
