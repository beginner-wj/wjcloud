package com.wj.server.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.server.domain.Resource;
import com.wj.server.domain.ResourceExample;
import com.wj.server.dto.ResourceDto;
import com.wj.server.dto.PageDto;
import com.wj.server.mapper.ResourceMapper;
import com.wj.server.util.CopyUtil;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Service
public class ResourceService {

    private  static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);

    @jakarta.annotation.Resource
    ResourceMapper resourceMapper;


    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();

        List<Resource> lists = resourceMapper.selectByExample(resourceExample);

        PageInfo<Resource> pageInfo = new PageInfo<>(lists);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> dtolists = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            ResourceDto dto = new ResourceDto();
            BeanUtils.copyProperties(lists.get(i),dto);
            dtolists.add(dto);
        }
        pageDto.setList(dtolists);
    }


    public void saveResource(ResourceDto resourceDto){
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto,resource);
        Date now = new Date();
        if(StringUtils.isEmpty(resource.getId())){
            resourceMapper.insert(resource);
            return;
        }else{
        }
        resourceMapper.updateByPrimaryKey(resource);
    }

    public void saveJson(String resourceJson){
       List<ResourceDto> jsonList = JSONArray.parseArray(resourceJson, ResourceDto.class);
       List<ResourceDto> list = new ArrayList<>();
       if(!CollectionUtils.isEmpty(jsonList)){
           for(ResourceDto d : jsonList){
               d.setParent("");
               add(list,d);
           }
       }
        LOG.info("共{}条",list.size());
       resourceMapper.deleteByExample(null);
       for(int i=0;i<list.size();i++){
           Resource resource = CopyUtil.copy(list.get(i),Resource.class);
           resourceMapper.insert(resource);
       }
    }

    public void add(List<ResourceDto> list,ResourceDto dto){
          list.add(dto);
          if(!CollectionUtils.isEmpty(dto.getChildren())){
               for(ResourceDto d:dto.getChildren()){
                   d.setParent(dto.getId());
                   add(list,d);
               }
          }
    }

    public void delResource(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }


    public List<ResourceDto> all() {
        ResourceExample example = new ResourceExample();
        example.setOrderByClause(" id asc ");
        List<Resource> resources = resourceMapper.selectByExample(example);
        List<ResourceDto> dtoList = CopyUtil.copyList(resources,ResourceDto.class);
        for(int i=dtoList.size() - 1;i>=0;i--){
            //当前要移动的记录
            ResourceDto child = dtoList.get(i);
            //如果没有父节点。则不用再往下
            if(StringUtils.isEmpty(child.getParent())){
                continue;
            }
            //查找父节点
            for(int j= i -1 ;j >= 0;j--){
                ResourceDto parent = dtoList.get(j);
                if(child.getParent().equals(parent.getId())){
                     if(CollectionUtils.isEmpty(parent.getChildren())){
                          parent.setChildren(new ArrayList<>());
                     }
                     //添加到最前面 否则会变成倒序
                    parent.getChildren().add(0,child);
                     //子节点找到父节点后。删除列表中的子节点
                    dtoList.remove(child);
                }
            }
        }
        return dtoList;
    }
}
