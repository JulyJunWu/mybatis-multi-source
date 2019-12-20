package com.multisource.demo.studentmapper;

import com.multisource.demo.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {

    Student selectOne();

    int insert(Student student);
}
