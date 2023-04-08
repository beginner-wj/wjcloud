package com.wj.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.Chapter;
import com.wj.server.domain.ChapterExample;
import com.wj.server.dto.ChapterDto;
import com.wj.server.dto.PageDto;
import com.wj.server.mapper.ChapterMapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    ChapterMapper chapterMapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();

        List<Chapter> lists = chapterMapper.selectByExample(chapterExample);

        PageInfo<Chapter> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            ChapterDto dto = new ChapterDto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    public void saveChapter(ChapterDto chapterDto){
        Chapter chapter = new Chapter();
        System.out.println("====");
        BeanUtils.copyProperties(chapterDto,chapter);
        Date now = new Date();
        if(StringUtils.isEmpty(chapter.getId())){

            chapter.setId(IdUtil.randomUUID());
            chapterMapper.insert(chapter);
            return;
        }else{
        }

        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void delChapter(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}
