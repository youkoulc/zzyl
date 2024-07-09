package com.zzyl.service.impl;

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
}
