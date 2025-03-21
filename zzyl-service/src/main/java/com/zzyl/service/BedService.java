package com.zzyl.service;

import com.zzyl.dto.BedDto;
import com.zzyl.vo.BedVo;

import java.util.List;

/**
 * 服务接口：BedService（床位管理服务）
 */
public interface BedService {


    /**
     * 根据id查询床位
     * @param id
     * @return
     */
    BedVo getBedById(Integer id);

    /**
     * 通过房间ID检索床位
     * @param roomId 房间ID
     * @return 床位视图对象列表
     */
    List<BedVo> getBedsByRoomId(Long roomId);

    /**
     * 创建床位
     * @param bedDto
     * @return
     */
    int creatBed(BedDto bedDto);

    /**
     * 更新床位
     * @param bedDto
     * @return
     */
    void updateBed(BedDto bedDto);

    /**
     * 删除床位
     * @param id
     * @return
     */
    void deleteBed(Integer id);
}
