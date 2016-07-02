package com.controller;

import com.models.Student;
import com.service.StudentService;
import com.util.Analysis;
import com.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class StudentController {
    private StudentService studentService;

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/getCode")
    public void getCode(HttpServletResponse response){
        CookieUtil.getCode();
    }

    @RequestMapping("/login")
    public String login(Student student,@RequestParam(value = "code") String code,HttpServletRequest request){
        Analysis analysis = new Analysis();
        analysis.getScores(student.getUsername(),student.getPassword(),code);
        List<String[]> list = analysis.getGradePoint();
        if(list==null){
            return "login.html";
        }
        studentService.login(student);
        request.setAttribute("grades",list.get(0));
        request.setAttribute("names",list.get(1));
        request.setAttribute("points",list.get(2));
        request.setAttribute("scores",list.get(3));
        request.setAttribute("this_gp",String.valueOf(list.get(5)).substring(0,6));
        request.setAttribute("gp",String.valueOf(list.get(4)).substring(0,6));
        request.setAttribute("sum_points",list.get(6));
        request.setAttribute("sub_types",list.get(7));
        return "detail.jsp";
    }
}
