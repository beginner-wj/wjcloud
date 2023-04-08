package com.wj.system.controller.admin;

import com.wj.server.domain.RoleUser;
import com.wj.server.dto.RoleUserDto;
import com.wj.server.dto.PageDto;
import com.wj.server.dto.ResponseDto;
import com.wj.server.service.RoleUserService;
import com.wj.server.util.ValidatorUtil;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/role_user")
public class RoleUserController {
    private  static final Logger LOG = LoggerFactory.getLogger(RoleUserController.class);

    public final static String BUSINESS_NAME = "角色用户关联表";

    @Resource
    RoleUserService role_userService;

    @PostMapping("/list")
    public ResponseDto getRoleUserList(@RequestBody  PageDto pageDto){
        role_userService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody  RoleUserDto role_userDto){
        LOG.info("role_userDto {} ",role_userDto);
        //保存校验
                ValidatorUtil.require(role_userDto.getRoleId(),"角色");
                ValidatorUtil.length(role_userDto.getRoleId(),"角色",1,40);
                ValidatorUtil.require(role_userDto.getUserId(),"用户");
                ValidatorUtil.length(role_userDto.getUserId(),"用户",1,40);

        ResponseDto responseDto = new ResponseDto();

        role_userService.saveRoleUser(role_userDto);
        responseDto.setContent(role_userDto);
        return responseDto;
    }
    @PostMapping("/del/{id}")
    public ResponseDto del(@PathVariable String id){
        LOG.info("id {} ",id);
        role_userService.delRoleUser(id);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
