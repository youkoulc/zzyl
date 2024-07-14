package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.ResourceVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description ResourceServiceImpl
 * @Author HeFeng
 * @Date 2024-07-14
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceMapper resourceMapper;

    /**
     * 获取资源列表
     *
     * @param resourceDto
     * @return
     */
    @Override
    public List<ResourceVo> getResourceList(ResourceDto resourceDto) {
        List<Resource> resourceList=resourceMapper.selectResourceList(BeanUtil.toBean(resourceDto, Resource.class));

        return BeanUtil.copyToList(resourceList,ResourceVo.class);
    }
}
