package com.dao;

import com.bean.Infotype;

public interface InfotypeMapper {
    int deleteByPrimaryKey(Integer infoid);

    int insert(Infotype record);

    int insertSelective(Infotype record);

    Infotype selectByPrimaryKey(Integer infoid);

    int updateByPrimaryKeySelective(Infotype record);

    int updateByPrimaryKey(Infotype record);
}