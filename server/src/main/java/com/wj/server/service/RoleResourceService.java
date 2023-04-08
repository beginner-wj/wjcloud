package com.wj.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.RoleResource;
import com.wj.server.domain.RoleResourceExample;
import com.wj.server.dto.RoleResourceDto;
import com.wj.server.dto.PageDto;
import com.wj.server.mapper.RoleResourceMapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Service
public class RoleResourceService {

    @Resource
    RoleResourceMapper role_resourceMapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleResourceExample role_resourceExample = new RoleResourceExample();

        List<RoleResource> lists = role_resourceMapper.selectByExample(role_resourceExample);

        PageInfo<RoleResource> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleResourceDto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            RoleResourceDto dto = new RoleResourceDto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    public void saveRoleResource(RoleResourceDto role_resourceDto){
        RoleResource role_resource = new RoleResource();
        BeanUtils.copyProperties(role_resourceDto,role_resource);
        Date now = new Date();
        if(StringUtils.isEmpty(role_resource.getId())){

            role_resource.setId(IdUtil.randomUUID());
            role_resourceMapper.insert(role_resource);
            return;
        }else{
        }

        role_resourceMapper.updateByPrimaryKey(role_resource);
    }

    public void delRoleResource(String id) {
        role_resourceMapper.deleteByPrimaryKey(id);
    }
}
