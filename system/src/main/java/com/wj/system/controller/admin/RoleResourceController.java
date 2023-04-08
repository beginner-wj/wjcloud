package com.wj.system.controller.admin;

import com.wj.server.domain.RoleResource;
import com.wj.server.dto.RoleResourceDto;
import com.wj.server.dto.PageDto;
import com.wj.server.dto.ResponseDto;
import com.wj.server.service.RoleResourceService;
import com.wj.server.util.ValidatorUtil;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/role_resource")
public class RoleResourceController {
    private  static final Logger LOG = LoggerFactory.getLogger(RoleResourceController.class);

    public final static String BUSINESS_NAME = "角色资源关联表";

    @Resource
    RoleResourceService role_resourceService;

    @PostMapping("/list")
    public ResponseDto getRoleResourceList(@RequestBody  PageDto pageDto){
        role_resourceService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody  RoleResourceDto role_resourceDto){
        LOG.info("role_resourceDto {} ",role_resourceDto);
        //保存校验
                ValidatorUtil.require(role_resourceDto.getRoleId(),"角色");
                ValidatorUtil.length(role_resourceDto.getRoleId(),"角色",1,40);
                ValidatorUtil.require(role_resourceDto.getResourceId(),"资源");
                ValidatorUtil.length(role_resourceDto.getResourceId(),"资源",1,40);

        ResponseDto responseDto = new ResponseDto();

        role_resourceService.saveRoleResource(role_resourceDto);
        responseDto.setContent(role_resourceDto);
        return responseDto;
    }
    @PostMapping("/del/{id}")
    public ResponseDto del(@PathVariable String id){
        LOG.info("id {} ",id);
        role_resourceService.delRoleResource(id);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
