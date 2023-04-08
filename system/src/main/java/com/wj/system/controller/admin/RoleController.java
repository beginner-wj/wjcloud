package com.wj.system.controller.admin;

import com.wj.server.domain.Role;
import com.wj.server.dto.RoleDto;
import com.wj.server.dto.PageDto;
import com.wj.server.dto.ResponseDto;
import com.wj.server.dto.RoleUserDto;
import com.wj.server.service.RoleService;
import com.wj.server.util.ValidatorUtil;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class RoleController {
    private  static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    public final static String BUSINESS_NAME = "角色表";

    @Resource
    RoleService roleService;

    @PostMapping("/list")
    public ResponseDto getRoleList(@RequestBody  PageDto pageDto){
        roleService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody  RoleDto roleDto){
        LOG.info("roleDto {} ",roleDto);
        //保存校验
                ValidatorUtil.require(roleDto.getName(),"角色");
                ValidatorUtil.length(roleDto.getName(),"角色",1,100);
                ValidatorUtil.require(roleDto.getRoledesc(),"描述");
                ValidatorUtil.length(roleDto.getRoledesc(),"描述",1,100);

        ResponseDto responseDto = new ResponseDto();

        roleService.saveRole(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    @PostMapping("/saveuser")
    public ResponseDto saveUser(@RequestBody RoleDto roleDto){
        LOG.info("roleDto {} ",roleDto);
        ResponseDto responseDto = new ResponseDto();
        roleService.saveuser(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody  RoleDto roleDto){
        LOG.info("roleDto==>{}",roleDto);
        ResponseDto responseDto = new ResponseDto();
        roleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    @PostMapping("/list-resource/{id}")
    public ResponseDto listResource(@PathVariable String id){
        LOG.info("id {} ",id);
        ResponseDto responseDto = new ResponseDto();
        List<String> ids = roleService.listResource(id);
        responseDto.setContent(ids);
        return responseDto;
    }


    @PostMapping("/list-user/{id}")
    public ResponseDto listUser(@PathVariable String id){
        LOG.info("id {} ",id);
        ResponseDto responseDto = new ResponseDto();
        List<String> ids = roleService.listuser(id);
        responseDto.setContent(ids);
        return responseDto;
    }
    @PostMapping("/del/{id}")
    public ResponseDto del(@PathVariable String id){
        LOG.info("id {} ",id);
        roleService.delRole(id);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
