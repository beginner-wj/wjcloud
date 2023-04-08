package com.wj.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.${Domain};
import com.wj.server.domain.${Domain}Example;
import com.wj.server.dto.${Domain}Dto;
import com.wj.server.dto.PageDto;
import com.wj.server.mapper.${Domain}Mapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Service
public class ${Domain}Service {

    @Resource
    ${Domain}Mapper ${domain}Mapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list  fieldList as field>
             <#if field.nameHump == 'sort'>
                 ${domain}Example.setOrderByClause("sort asc");
             </#if>
        </#list>

        List<${Domain}> lists = ${domain}Mapper.selectByExample(${domain}Example);

        PageInfo<${Domain}> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            ${Domain}Dto dto = new ${Domain}Dto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    public void save${Domain}(${Domain}Dto ${domain}Dto){
        ${Domain} ${domain} = new ${Domain}();
        BeanUtils.copyProperties(${domain}Dto,${domain});
        Date now = new Date();
        if(StringUtils.isEmpty(${domain}.getId())){

            ${domain}.setId(IdUtil.randomUUID());
            <#list fieldList as field>
                <#if field.nameHump=="createAt">
                    ${domain}.setCreateAt(now);
                </#if>
                <#if field.nameHump=="updateAt">
                    ${domain}.setUpdateAt(now);
                </#if>
            </#list>
            ${domain}Mapper.insert(${domain});
            return;
        }else{
    <#list fieldList as field>
        <#if field.nameHump=="updateAt">
            ${domain}.setUpdateAt(now);
        </#if>
    </#list>
        }

        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    public void del${Domain}(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
