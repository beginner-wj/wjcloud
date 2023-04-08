package com.wj.system.controller.admin;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wj.server.domain.User;
import com.wj.server.dto.*;
import com.wj.server.service.UserService;
import com.wj.server.util.ValidatorUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    private  static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public final static String BUSINESS_NAME = "用户";

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    UserService userService;

    @PostMapping("/list")
    public ResponseDto getUserList(@RequestBody  PageDto pageDto){
        userService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/all")
    public ResponseDto getAllUser(){
        ResponseDto responseDto = new ResponseDto();
        List<UserDto> userDtos = userService.getAllUser();
        responseDto.setContent(userDtos);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody  UserDto userDto){
        LOG.info("userDto {} ",userDto);
        //保存校验
                ValidatorUtil.require(userDto.getLoginName(),"登录名");
                ValidatorUtil.length(userDto.getLoginName(),"登录名",1,50);
                ValidatorUtil.length(userDto.getName(),"昵称",1,50);
                ValidatorUtil.length(userDto.getPassword(),"密码",1,32);

        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        userService.saveUser(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody  UserDto userDto){
        LOG.info("userDto {} ",userDto);
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        userService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    @PostMapping("/del/{id}")
    public ResponseDto del(@PathVariable String id){
        LOG.info("id {} ",id);
        userService.delUser(id);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody  UserDto userDto, HttpServletRequest request){
        LOG.info("userDto {} ",userDto);
        //根据验证码token去获取缓存中的验证码 看是否一致
        //String imageCode = (String)request.getSession().getAttribute(userDto.getImageCodeToken());
        String imageCode = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        ResponseDto responseDto = new ResponseDto();
        if(StringUtils.isEmpty(imageCode)){
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            return responseDto;
         }
        if(!imageCode.toUpperCase().equals(userDto.getImageCode().toUpperCase())){
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码错误");
            return responseDto;
        }else{
            redisTemplate.delete(userDto.getImageCodeToken());
            //request.getSession().removeAttribute(userDto.getImageCodeToken());
        }

        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        LoginUserDto loginUserDto = userService.login(userDto);
        String token = IdUtil.randomUUID();
        loginUserDto.setToken(token);
        //TODO 这里的时间需要加到redis里
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto),3600, TimeUnit.SECONDS);
        //request.setAttribute(Constants.LOGINUSER,loginUserDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

    @PostMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token, HttpServletRequest request){
        LOG.info("token {} ",token);
        ResponseDto responseDto = new ResponseDto();
        redisTemplate.delete(token);
        //request.setAttribute(Constants.LOGINUSER,null);
        LOG.info("redis中删除token,{}",token);
        return responseDto;
    }
}
