package com.wj.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.RoleUser;
import com.wj.server.domain.RoleUserExample;
import com.wj.server.dto.RoleUserDto;
import com.wj.server.dto.PageDto;
import com.wj.server.mapper.RoleUserMapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Service
public class RoleUserService {

    @Resource
    RoleUserMapper role_userMapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleUserExample role_userExample = new RoleUserExample();

        List<RoleUser> lists = role_userMapper.selectByExample(role_userExample);

        PageInfo<RoleUser> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            RoleUserDto dto = new RoleUserDto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    public void saveRoleUser(RoleUserDto role_userDto){
        RoleUser role_user = new RoleUser();
        BeanUtils.copyProperties(role_userDto,role_user);
        Date now = new Date();
        if(StringUtils.isEmpty(role_user.getId())){

            role_user.setId(IdUtil.randomUUID());
            role_userMapper.insert(role_user);
            return;
        }else{
        }

        role_userMapper.updateByPrimaryKey(role_user);
    }

    public void delRoleUser(String id) {
        role_userMapper.deleteByPrimaryKey(id);
    }
}
