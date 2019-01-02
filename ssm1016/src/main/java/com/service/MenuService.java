package com.service;

import com.bean.Menu;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MenuService {
    int deleteByPrimaryKey(Integer menuid);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuid);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    //根据角色id查询菜单集合
    public List<Menu> findbyroleid(int roleid);
    //查询全部菜单
    public List<Menu> getall();
    //
    PageInfo getall2(int index,int size,int upmenuid);
    List getall3();
    int  deletebyids(String[] ids) ;
}