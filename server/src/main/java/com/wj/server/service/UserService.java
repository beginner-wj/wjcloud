package com.wj.server.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.User;
import com.wj.server.domain.UserExample;
import com.wj.server.dto.LoginUserDto;
import com.wj.server.dto.ResourceDto;
import com.wj.server.dto.UserDto;
import com.wj.server.dto.PageDto;
import com.wj.server.exception.BusinessException;
import com.wj.server.exception.BusinessExceptionCode;
import com.wj.server.mapper.UserMapper;
import com.wj.server.mapper.my.MyUserMapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Date;


@Service
public class UserService {
    private  static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Resource
    UserMapper userMapper;

    @Resource
    MyUserMapper myUserMapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        UserExample userExample = new UserExample();

        List<User> lists = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    /****
     * 重置密码
     * @param userDto
     */
    public void savePassword(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void saveUser(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        Date now = new Date();
        if(StringUtils.isEmpty(user.getId())){
            User userDb = this.selectByLoginName(userDto.getLoginName());
            if(userDb != null){
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
            user.setId(IdUtil.randomUUID());
            userMapper.insert(user);
            return;
        }
        user.setPassword(null);
        //选择性更新
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void delUser(String id) {
        userMapper.deleteByPrimaryKey(id);
    }


    /***
     * 根据登录名查询登录信息
     * @param loginName
     * @return
     */
    public User selectByLoginName(String loginName){
        UserExample userExample =  new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    public LoginUserDto login(UserDto userDto) {
        User user = selectByLoginName(userDto.getLoginName());
        if(user == null){
            LOG.info("用户名不存在:" + userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGINNAME_OR_PWD_ERROR);
        }
        String dbPwd = user.getPassword();
        if(!dbPwd.equals(userDto.getPassword())){
            LOG.info("密码错误:" + userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGINNAME_OR_PWD_ERROR);
        }
        LoginUserDto loginUserDto = CopyUtil.copy(user,LoginUserDto.class);
        //为登录用户获取权限
        setAuth(loginUserDto);
        return loginUserDto;
    }


    /***
     * 获取所有用户
     * @return
     */
    public List<UserDto> getAllUser() {
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        return CopyUtil.copyList(users,UserDto.class);
    }

    /***
     * 为登录用户获取权限
     * @param loginUserDto
     */
    private void setAuth(LoginUserDto loginUserDto){
         List<ResourceDto> resourceDtos = myUserMapper.findResources(loginUserDto.getId());
         loginUserDto.setResources(resourceDtos);

         //整理所有有权限的请求。用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if(!CollectionUtils.isEmpty(resourceDtos)){
             for(int i=0,l = resourceDtos.size();i<l;i++){
                  ResourceDto resourceDto = resourceDtos.get(i);
                  String arrayString = resourceDto.getRequest();
                  List<String> requestList = JSON.parseArray(arrayString,String.class);
                  if(!CollectionUtils.isEmpty(requestList)){
                      requestSet.addAll(requestList);
                  }
             }
        }
        LOG.info("有权限的请求：{}",requestSet);
        loginUserDto.setRequest(requestSet);
    }
}
