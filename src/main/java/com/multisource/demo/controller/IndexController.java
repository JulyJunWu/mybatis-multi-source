package com.multisource.demo.controller;

import com.multisource.demo.model.Student;
import com.multisource.demo.model.User;
import com.multisource.demo.service.StudentService;
import com.multisource.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JunWu
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @GetMapping("u")
    public User selectUser() {
        return userService.selectOne();
    }

    @GetMapping("s")
    public Student selectStudent() {
        return studentService.selectOne();
    }

    @GetMapping("addS")
    public int addStudent(Student student) {
        return studentService.insert(student);
    }

    @GetMapping("addU")
    public int addUser(User user) {
        return userService.insert(user);
    }
}
