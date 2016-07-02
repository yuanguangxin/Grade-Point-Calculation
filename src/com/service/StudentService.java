package com.service;

import com.mapper.StudentMapper;
import com.models.Student;
import com.models.StudentExample;

public class StudentService {
    private StudentMapper studentMapper;

    public StudentMapper getStudentMapper() {
        return studentMapper;
    }

    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public void login(Student student){
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andUsernameEqualTo(student.getUsername());
        if(studentMapper.selectByExample(studentExample).size()==0) {
            studentMapper.insert(student);
        }
    }
}
