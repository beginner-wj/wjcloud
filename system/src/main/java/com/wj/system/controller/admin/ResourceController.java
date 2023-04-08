package com.wj.system.controller.admin;

import com.wj.server.domain.Resource;
import com.wj.server.dto.ResourceDto;
import com.wj.server.dto.PageDto;
import com.wj.server.dto.ResponseDto;
import com.wj.server.service.ResourceService;
import com.wj.server.util.ValidatorUtil;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {
    private  static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    public final static String BUSINESS_NAME = "资源表";

    @jakarta.annotation.Resource
    ResourceService resourceService;

    @PostMapping("/load-tree")
    public ResponseDto getTreeData(){
        ResponseDto responseDto = new ResponseDto();
        List<ResourceDto> dtoList = resourceService.all();
        responseDto.setContent(dtoList);
        return responseDto;
    }

    @PostMapping("/list")
    public ResponseDto getResourceList(@RequestBody  PageDto pageDto){
        resourceService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody  String resourceStr){
        LOG.info("resourceStr {} ",resourceStr);
        ValidatorUtil.require(resourceStr,"资源");
        ResponseDto responseDto = new ResponseDto();
        resourceService.saveJson(resourceStr);
        return responseDto;
    }
    @PostMapping("/del/{id}")
    public ResponseDto del(@PathVariable String id){
        LOG.info("id {} ",id);
        resourceService.delResource(id);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
