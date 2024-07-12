package com.zzyl.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * @Description ReservationMapper
 * @Author HeFeng
 * @Date 2024-07-12
 */
@Mapper
public interface ReservationMapper {
    /**
     * 查询当天取消预约数量
     * @return
     */
    Integer selectCancelledCount(Long userId, LocalDateTime zero, LocalDateTime now);

}
