package com.service.impl;

import com.bean.Classes;
import com.dao.ClassesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassesMapper classesMapper;
    @Override
    public PageInfo getall(String name,String classnum,int pageindex,int size,int[] ids,String state) {

        //封装模糊查条件
        Map map=new HashMap();
        map.put("cname",name);
        map.put("clanum",classnum);
        map.put("ids",ids);
        map.put("state",state);
        PageHelper.startPage(pageindex,size);
        List list= classesMapper.getall(map);

        PageInfo pi=new PageInfo(list);

        return pi;
    }

    @Override
    public int deleteByPrimaryKey(Integer classid) {
        return 0;
    }

    @Override
    @Transactional
    public int insert(Classes record) {
        return classesMapper.insert(record);
    }

    @Override
    public int insertSelective(Classes record) {
        return 0;
    }

    @Override
    public Classes selectByPrimaryKey(Integer classid) {
        return null;
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Classes record) {
        return classesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Classes record) {
        return 0;
    }
}
