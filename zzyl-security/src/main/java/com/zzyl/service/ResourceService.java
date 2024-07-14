package com.zzyl.service;

import com.zzyl.dto.ResourceDto;
import com.zzyl.vo.ResourceVo;

import java.util.List;

/**
 * @Description ResourceService
 * @Author HeFeng
 * @Date 2024-07-14
 */
public interface ResourceService {
    /**
     * 获取资源列表
     * @param resourceDto
     * @return
     */
    List<ResourceVo> getResourceList(ResourceDto resourceDto);
}
