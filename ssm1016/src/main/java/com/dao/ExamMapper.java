package com.dao;

import com.bean.Exam;

public interface ExamMapper {
    int insert(Exam record);

    int insertSelective(Exam record);
}