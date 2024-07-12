package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.entity.Reservation;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.ReservationMapper;
import com.zzyl.service.ReservationService;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description ReservationServiceImpl
 * @Author HeFeng
 * @Date 2024-07-12
 */
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationMapper reservationMapper;

    /**
     * 查询取消预约数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getCancelledCount(Long userId) {
        Integer count=reservationMapper.selectCancelledCount(userId, LocalDateTimeUtil.beginOfDay(LocalDateTime.now()),LocalDateTime.now());
        if (count == null) {
            count =0;
        }
        return count;
    }

    /**
     * 查询指定时间当天每个时间段剩余预约次数
     *
     * @param time
     */
    @Override
    public List<TimeCountVo> getCountByTime(Long time) {
        LocalDateTime localDateTime = LocalDateTimeUtil.of(time);


        List<TimeCountVo> timeCountVoList=reservationMapper.selectCountByTime(localDateTime,localDateTime.plusDays(1));
        return timeCountVoList;
    }

    /**
     * 新增预约
     *
     * @param reservationDto
     * @return
     */
    @Override
    public void add(ReservationDto reservationDto) {
        // TODO
        reservationDto.setStatus(0);
        Reservation reservation = BeanUtil.toBean(reservationDto, Reservation.class);
        try {
            reservationMapper.insert(reservation);
        } catch (Exception e) {
            throw new BaseException(BasicEnum.TIME_ALREADY_RESERVATED_BY_PHONE);
        }
    }

    /**
     * 分页查询预约记录
     *
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageResponse<ReservationVo> getByPage(Integer pageNum, Integer pageSize, Integer status) {
        PageHelper.startPage(pageNum,pageSize);
        Page<ReservationVo> page =reservationMapper.selectByPage(status);
        PageResponse<ReservationVo> pageResponse = new PageResponse<>(page);
        pageResponse.setRecords(page.getResult());
        return pageResponse;
    }
}
