package edu.hlju.csti.web.sq.controller;


import edu.hlju.csti.web.sq.model.Rank;
import edu.hlju.csti.web.sq.model.Student;
import edu.hlju.csti.web.sq.service.RankService;
import edu.hlju.csti.web.sq.service.StudentService;
import edu.hlju.csti.web.sq.util.Analysis;
import edu.hlju.csti.web.sq.util.CookieUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {
    public CookieUtil cookieUtil = new CookieUtil();

    @Autowired
    private StudentService studentService;
    @Autowired
    private RankService rankService;
    @Value("#{web_config['captcha.url']}")
    private String codeUrl;


    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public ModelAndView login() {
        codeUrl = codeUrl.replace("[n]", String.valueOf(RandomUtils.nextInt(1,3)));
        return new ModelAndView("login","CODE_IMG_URL",codeUrl);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView login(Student student, @RequestParam(value = "code") String code, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Cookie c1 = new Cookie("user", student.getUsername());
        Cookie c2 = new Cookie("pass", student.getPassword().split("document")[0]);
        response.addCookie(c1);
        response.addCookie(c2);
        student.setPassword(student.getPassword().split("document")[0]);
        Analysis analysis = new Analysis();
        analysis.getScores(cookieUtil, student.getUsername(), student.getPassword(), code);
        List list = analysis.getGradePoint();
        if (list == null) {
            return new ModelAndView("redirect:/login");
        }
        studentService.login(student);
        Rank rank = new Rank();
        rank.setStuId(student.getUsername());
        map.put("grades", list.get(0));
        map.put("names", list.get(1));
        map.put("points", list.get(2));
        map.put("scores", list.get(3));
        if (String.valueOf(list.get(5)).length() >= 6) {
            map.put("this_gp", String.valueOf(list.get(5)).substring(0, 6));
        } else {
            map.put("this_gp", String.valueOf(list.get(5)));
        }
        if (String.valueOf(list.get(4)).length() >= 6) {
            map.put("gp", String.valueOf(list.get(4)).substring(0, 6));
            rank.setPoint(String.valueOf(list.get(4)).substring(0, 6));
        } else {
            map.put("gp", String.valueOf(list.get(4)));
            rank.setPoint(String.valueOf(list.get(4)));
        }
        map.put("sum_points", list.get(6));
        map.put("sub_types", list.get(7));
        map.put("info", list.get(8));
        rank.setInfo(list.get(8).toString().split("，")[0]);
        rankService.insert(rank);
        Object object = list.get(9);
        ArrayList<String[]> arrayList = (ArrayList<String[]>) object;
        if (arrayList.size() < 4) {
            int need = 4 - arrayList.size();
            for (int i = 0; i < need; i++) {
                String[] t = new String[2];
                t[0] = "";
                t[1] = "";
                arrayList.add(t);
            }
        }
        ArrayList<String> year_point = new ArrayList<>();
        for (String[] anArrayList : arrayList) {
            year_point.add(anArrayList[1]);
        }
        map.put("test", year_point);
        double[] s = (double[]) list.get(10);
        map.put("poi", s);
        List<Rank> list1 = rankService.getRank();
        map.put("ranking", list1);
        return new ModelAndView("detail", map);
    }
}
