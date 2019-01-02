package com.service.impl;

import com.bean.Major;
import com.dao.MajorMapper;
import com.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorMapper majorMapper;
    @Override
    public int deleteByPrimaryKey(Integer majorid) {
        return 0;
    }

    @Override
    public int insert(Major record) {
        return 0;
    }

    @Override
    public int insertSelective(Major record) {
        return 0;
    }

    @Override
    public Major selectByPrimaryKey(Integer majorid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Major record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Major record) {
        return 0;
    }

    @Override
    public List<Major> findbydeptid(int did) {
        return majorMapper.findbydeptid(did);
    }
}
