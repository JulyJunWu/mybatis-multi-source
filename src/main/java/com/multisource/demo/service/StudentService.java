package com.multisource.demo.service;

import com.multisource.demo.model.Student;
import com.multisource.demo.studentmapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JunWu
 * service
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public Student selectOne() {
        return studentMapper.selectOne();
    }

    /**
     * 由于有多数据源,故有多个事务管理器,需要指定此处的事务管理器
     *
     * @param student
     * @return
     */
    @Transactional(transactionManager = "studentTransaction", rollbackFor = Exception.class)
    public int insert(Student student) {
        int insert = studentMapper.insert(student);
        //int a = 1 / 0;
        return insert;
    }

}
