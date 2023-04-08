package com.wj.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.Section;
import com.wj.server.domain.SectionExample;
import com.wj.server.dto.SectionDto;
import com.wj.server.dto.PageDto;
import com.wj.server.mapper.SectionMapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class SectionService {

    @Resource
    SectionMapper sectionMapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        SectionExample sectionExample = new SectionExample();
                 SectionExample.setOrderByClause("sort asc");

        List<Section> lists = sectionMapper.selectByExample(sectionExample);

        PageInfo<Section> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            SectionDto dto = new SectionDto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    public void saveSection(SectionDto sectionDto){
        Section section = new Section();
        System.out.println("====");
        BeanUtils.copyProperties(sectionDto,section);
        Date now = new Date();
        if(StringUtils.isEmpty(section.getId())){

            section.setId(IdUtil.randomUUID());
                    section.setCreateAt(now);
            sectionMapper.insert(section);
            return;
        }else{
            section.setUpdateAt(now);
        }

        sectionMapper.updateByPrimaryKey(section);
    }

    public void delSection(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
