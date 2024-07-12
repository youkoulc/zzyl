package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.Reservation;
import com.zzyl.vo.ReservationVo;
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

    /**
     * 分页查询预约记录
     *
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    Page<ReservationVo> selectByPage(Integer status);

    /**
     * 取消预约
     * @param id
     * @return
     */
    void cancel(Long userId, Long id);
}
