package com.wj.server.mapper.my;

import com.wj.server.dto.ResourceDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyUserMapper {
    List<ResourceDto> findResources(@Param("userId") String userId);
}
