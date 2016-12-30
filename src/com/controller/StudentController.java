package com.controller;

import com.models.Message;
import com.models.Rank;
import com.models.Student;
import com.service.MessageService;
import com.service.RankService;
import com.service.StudentService;
import com.util.Analysis;
import com.util.CookieUtil;
import com.util.Dates;
import com.util.Decode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    public CookieUtil cookieUtil = new CookieUtil();
    public String path = "";
    private StudentService studentService;
    private RankService rankService;
    private MessageService messageService;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public RankService getRankService() {
        return rankService;
    }

    public void setRankService(RankService rankService) {
        this.rankService = rankService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws Exception{
        if(session.getAttribute("path")!=null){
            File file = new File(session.getAttribute("path").toString());
            if(file.exists()){
                file.delete();
            }
        }
        String s = Dates.getDate();
        String ss = String.valueOf(Math.random()*10);
        cookieUtil.getCode(s+ss);
        response.getWriter().print(s+ss);
        File file = new File("/Users/yuanguangxin/Desktop/etc/Grade Point Calculation/out/artifacts/Grade_Point_Calculation_war_exploded/img");
        if (!file.exists()){
            file.mkdir();
        }
        path = "/Users/yuanguangxin/Desktop/etc/Grade Point Calculation/out/artifacts/Grade_Point_Calculation_war_exploded/img/code"+s+ss+".bmp";
        session.setAttribute("path",path);
    }

    @RequestMapping("/login")
    public String login(Student student,@RequestParam(value = "code") String code,HttpServletRequest request,HttpServletResponse response){
        Cookie c1=new Cookie("user",student.getUsername());
        Cookie c2=new Cookie("pass",new String(Decode.decode(student.getPassword())));
        response.addCookie(c1);
        response.addCookie(c2);
        student.setPassword(new String(Decode.decode(student.getPassword())));
        Analysis analysis = new Analysis();
        analysis.getScores(cookieUtil,student.getUsername(),student.getPassword(),code);
        List list = analysis.getGradePoint();
        if(list==null){
            return "/login.jsp";
        }
        studentService.login(student);
        Rank rank = new Rank();
        rank.setStuId(student.getUsername());
        request.setAttribute("grades",list.get(0));
        request.setAttribute("names",list.get(1));
        request.setAttribute("points",list.get(2));
        request.setAttribute("scores",list.get(3));
        if(String.valueOf(list.get(5)).length()>=6){
            request.setAttribute("this_gp",String.valueOf(list.get(5)).substring(0,6));
        }else {
            request.setAttribute("this_gp",String.valueOf(list.get(5)));
        }
        if(String.valueOf(list.get(4)).length()>=6){
            request.setAttribute("gp",String.valueOf(list.get(4)).substring(0,6));
            rank.setPoint(String.valueOf(list.get(4)).substring(0,6));
        }else {
            request.setAttribute("gp",String.valueOf(list.get(4)));
            rank.setPoint(String.valueOf(list.get(4)));
        }
        request.setAttribute("sum_points",list.get(6));
        request.setAttribute("sub_types",list.get(7));
        request.setAttribute("info",list.get(8));
        rank.setInfo(list.get(8).toString().split("，")[0]);
        rankService.insert(rank);
        Object object = list.get(9);
        ArrayList<String[]> arrayList = (ArrayList<String[]>)object;
        if(arrayList.size()<4){
            int need = 4 - arrayList.size();
            for(int i=0;i<need;i++){
                String[] t = new String[2];
                t[0] = "";
                t[1] = "";
                arrayList.add(t);
            }
        }
        ArrayList<String> year_point = new ArrayList<>();
        for(int i=0;i<arrayList.size();i++){
            year_point.add(arrayList.get(i)[1]);
        }
        request.setAttribute("test",year_point);
        double[] s = (double[]) list.get(10);
        request.setAttribute("poi",s);
        List<Rank> list1 = rankService.getRank();
        request.setAttribute("ranking",list1);
        List<Message> list2 = messageService.getAllMessages();
        request.setAttribute("messages",list2);
        request.setAttribute("stuId",student.getUsername());
        return "detail.jsp";
    }

    @RequestMapping("/addMessage")
    public void addMessage(Message message,HttpServletResponse response){
        message.setTimes(Dates.getDates());
        message.setName(message.getName().split("，")[0]);
        messageService.addMessage(message);
    }

    @RequestMapping("/pk")
    public void pk(@RequestParam(value = "stuId")String stuId, @RequestParam(value = "id") String id,HttpServletResponse response) throws Exception{
        int st = rankService.pk(stuId,id);
        response.getWriter().print(st);
    }
}
