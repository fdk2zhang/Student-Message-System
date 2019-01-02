package com.service;

import com.bean.Classes;
import com.github.pagehelper.PageInfo;

public interface ClassService {
    //查询全部的方法
    public PageInfo getall(String name,String classnum,int pageindex,int size,int[] ids,String state);

    int deleteByPrimaryKey(Integer classid);

    int insert(Classes record);

    int insertSelective(Classes record);

    Classes selectByPrimaryKey(Integer classid);

    int updateByPrimaryKeySelective(Classes record);

    int updateByPrimaryKey(Classes record);
}
