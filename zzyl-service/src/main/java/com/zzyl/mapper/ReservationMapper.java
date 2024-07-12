package com.zzyl.mapper;

import com.zzyl.entity.Reservation;
import com.zzyl.vo.TimeCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

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


    /**
     * 查询指定时间当天每个时间段剩余预约次数
     *
     * @param
     */
    List<TimeCountVo> selectCountByTime(LocalDateTime beganTime, LocalDateTime endTime);

    /**
     * 新增预约
     * @param reservation
     * @return
     */
    void insert(Reservation reservation);
}
