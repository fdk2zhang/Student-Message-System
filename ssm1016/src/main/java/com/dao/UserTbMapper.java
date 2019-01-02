package com.dao;

import com.bean.UserTb;

import java.util.List;
import java.util.Map;

public interface UserTbMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserTb record);

    int insertSelective(UserTb record);

    UserTb selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserTb record);

    int updateByPrimaryKey(UserTb record);
    //登录
    UserTb login(String name);
    //查询
    List findall(Map map);

}