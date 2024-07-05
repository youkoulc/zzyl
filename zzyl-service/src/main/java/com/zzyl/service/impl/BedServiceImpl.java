package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.dto.BedDto;
import com.zzyl.entity.Bed;
import com.zzyl.mapper.BedMapper;
import com.zzyl.service.BedService;
import com.zzyl.vo.BedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedMapper bedMapper;

    @Override
    public List<BedVo> getBedsByRoomId(Long roomId) {
        return bedMapper.getBedsByRoomId(roomId);
    }

    /**
     * 创建床位
     * @param bedDto
     * @return
     */
    @Override
    public int creatBed(BedDto bedDto) {
        Bed bed = BeanUtil.toBean(bedDto, Bed.class);
        // 补充信息
        bed.setCreateTime(LocalDateTime.now());
        bed.setBedStatus(0);
        bed.setCreateBy(1L);

        return bedMapper.addBed(bed);
    }

}

