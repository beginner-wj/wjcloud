package com.wj.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.*;
import com.wj.server.dto.RoleDto;
import com.wj.server.dto.PageDto;
import com.wj.server.dto.RoleUserDto;
import com.wj.server.mapper.RoleMapper;
import com.wj.server.mapper.RoleResourceMapper;
import com.wj.server.mapper.RoleUserMapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Service
public class RoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleResourceMapper roleResourceMapper;

    @Resource
    RoleUserMapper roleUserMapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleExample roleExample = new RoleExample();

        List<Role> lists = roleMapper.selectByExample(roleExample);

        PageInfo<Role> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            RoleDto dto = new RoleDto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    public void saveRole(RoleDto roleDto){
        Role role = new Role();
        BeanUtils.copyProperties(roleDto,role);
        Date now = new Date();
        if(StringUtils.isEmpty(role.getId())){

            role.setId(IdUtil.randomUUID());
            roleMapper.insert(role);
            return;
        }else{
        }

        roleMapper.updateByPrimaryKey(role);
    }

    public void delRole(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void saveResource(RoleDto roleDto) {
        //删除之前的
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdEqualTo(roleDto.getId());
        roleResourceMapper.deleteByExample(roleResourceExample);
        String roleId = roleDto.getId();
        List<String>  resourceIds = roleDto.getResourceIds();
        for(int i=0;i<resourceIds.size();i++){
            RoleResource roleResource = new RoleResource();
            roleResource.setId(IdUtil.randomUUID());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }


    /***
     * 根据角色id获取所有勾选的资源id
     * @param id
     * @return
     */
    public List<String> listResource(String id) {
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdEqualTo(id);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        List<String> ids = new ArrayList<>();
        for(int i=0;i<roleResources.size();i++){
            ids.add(roleResources.get(i).getResourceId());
        }
        return ids;
    }

    /****
     * 保存用户-角色
     * @param roleDto
     */
    @Transactional
    public void saveuser(RoleDto roleDto) {
        String roleId = roleDto.getId();
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRoleIdEqualTo(roleId);
        roleUserMapper.deleteByExample(roleUserExample);

        List<String> userIds = roleDto.getUserIds();
        for(int i=0;i<userIds.size();i++){
            String userId = userIds.get(i);
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userId);
            roleUser.setId(IdUtil.randomUUID());
            roleUserMapper.insert(roleUser);
        }
    }


    /***
     * 角色下的用户
     * @param id
     * @return
     */
    public List<String> listuser(String id) {
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRoleIdEqualTo(id);
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);
        List<String> userids = new ArrayList<>();
        for(int i=0;i<roleUsers.size();i++){
            userids.add(roleUsers.get(i).getUserId());
        }
        return userids;
    }
}
