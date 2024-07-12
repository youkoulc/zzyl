package com.zzyl.service.impl;

import com.zzyl.mapper.ReservationMapper;
import com.zzyl.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description ReservationServiceImpl
 * @Author HeFeng
 * @Date 2024-07-12
 */
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationMapper reservationMapper;

    /**
     * 查询取消预约数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getCancelledCount(Long userId) {
        Integer count=reservationMapper.selectCancelledCount(userId, LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0),LocalDateTime.now());
        return count;
    }

}
