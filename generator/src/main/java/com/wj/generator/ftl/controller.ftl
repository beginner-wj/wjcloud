package com.wj.${module}.controller.admin;

import com.wj.server.domain.${Domain};
import com.wj.server.dto.${Domain}Dto;
import com.wj.server.dto.PageDto;
import com.wj.server.dto.ResponseDto;
import com.wj.server.service.${Domain}Service;
import com.wj.server.util.ValidatorUtil;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {
    private  static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);

    public final static String BUSINESS_NAME = "${tableNamCn}";

    @Resource
    ${Domain}Service ${domain}Service;

    @PostMapping("/list")
    public ResponseDto get${Domain}List(@RequestBody  PageDto pageDto){
        ${domain}Service.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody  ${Domain}Dto ${domain}Dto){
        LOG.info("${domain}Dto {} ",${domain}Dto);
        //保存校验
        <#list fieldList as field>
            <#if field.name != "id" && field.nameHump != "createAt" && field.nameHump != "updateAt"
               && field.nameHump != "sort">
            <#if !field.nullAble>
                ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(),"${field.nameCn}");
                </#if>
            <#if (field.length > 0)>
                ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(),"${field.nameCn}",1,${field.length?c});
            </#if>
            </#if>
        </#list>

        ResponseDto responseDto = new ResponseDto();

        ${domain}Service.save${Domain}(${domain}Dto);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }
    @PostMapping("/del/{id}")
    public ResponseDto del(@PathVariable String id){
        LOG.info("id {} ",id);
        ${domain}Service.del${Domain}(id);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
