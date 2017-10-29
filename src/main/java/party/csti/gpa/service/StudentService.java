package party.csti.gpa.service;

import org.springframework.stereotype.Service;
import party.csti.gpa.dao.StudentMapper;
import party.csti.gpa.model.Information;
import party.csti.gpa.model.Student;
import party.csti.gpa.model.StudentExample;
import party.csti.gpa.model.StudentView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;

    public void login(Student student) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andStuIdEqualTo(student.getStuId());
        if (studentMapper.selectByExample(studentExample).size() == 0) {
            studentMapper.insert(student);
        } else {
            Student stu = studentMapper.selectByExample(studentExample).get(0);
            stu.setPassword(student.getPassword());
            studentMapper.updateByPrimaryKey(stu);
        }
    }

    public String getPassword(String stuId) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andStuIdEqualTo(stuId);
        return studentMapper.selectByExample(studentExample).get(0).getPassword();
    }

    public List<StudentView> search(List<Information> infos) {
        List<StudentView> list = new ArrayList<>();
        for (int i = 0; i < infos.size(); i++) {
            StudentExample studentExample = new StudentExample();
            StudentExample.Criteria criteria = studentExample.createCriteria();
            criteria.andStuIdEqualTo(infos.get(i).getStuId());
            if (studentMapper.selectByExample(studentExample).size() > 0) {
                StudentView studentView = new StudentView();
                studentView.setInfo(infos.get(i).getInfo());
                studentView.setStuId(studentMapper.selectByExample(studentExample).get(0).getStuId());
                studentView.setPassword(studentMapper.selectByExample(studentExample).get(0).getPassword());
                list.add(studentView);
            }
        }
        return list;
    }
}
