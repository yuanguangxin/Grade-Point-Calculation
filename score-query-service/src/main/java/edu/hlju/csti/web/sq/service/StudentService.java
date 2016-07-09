package edu.hlju.csti.web.sq.service;


import edu.hlju.csti.web.sq.mapper.StudentMapper;
import edu.hlju.csti.web.sq.model.Student;
import edu.hlju.csti.web.sq.model.StudentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public StudentMapper getStudentMapper() {
        return studentMapper;
    }

    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public void login(Student student) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andUsernameEqualTo(student.getUsername());
        if (studentMapper.selectByExample(studentExample).size() == 0) {
            studentMapper.insert(student);
        }
    }
}
