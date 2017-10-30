package party.csti.gpa.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import party.csti.gpa.analysis.Analysis;
import party.csti.gpa.model.Information;
import party.csti.gpa.model.Student;
import party.csti.gpa.model.StudentView;
import party.csti.gpa.service.InfoService;
import party.csti.gpa.service.MessageService;
import party.csti.gpa.service.StudentService;
import party.csti.gpa.util.http.HttpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@Scope("session")
public class StudentController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private StudentService studentService;
    @Resource
    private InfoService infoService;
    @Resource
    private HttpUtil httpUtil;
    @Value("#{config['admin.user']}")
    private String user;
    @Value("#{config['admin.pass']}")
    private String pass;

    @ResponseBody
    @RequestMapping("/code")
    public String getCode() {
        String code = httpUtil.getCode();
        return code;
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value = "code") String code, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            Student student = new Student();
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("user")) {
                    student.setStuId(cookies[i].getValue());
                } else if (cookies[i].getName().equals("pass")) {
                    student.setPassword(cookies[i].getValue());
                }
            }
            if (student.getStuId().equals(user) && student.getPassword().equals(pass)) {
                session.setAttribute("admin", "true");
                return "/WEB-INF/admin/browse.jsp";
            }
            Analysis analysis = new Analysis();
            Map map = analysis.getGradePoint(httpUtil, student.getStuId(), student.getPassword(), code);
            if (map == null) {
                return "/page/login.jsp";
            }
            studentService.login(student);
            Information info = new Information();
            info.setStuId(student.getStuId());
            info.setInfo(map.get("userInfo").toString().split("，")[0]);
            info.setCredit(map.get("credit").toString());
            info.setNpoint(map.get("this_gp").toString());
            info.setTpoint(map.get("gp").toString());
            infoService.updateInfo(info);
            map.put("rank", infoService.getTpointRank());
            request.setAttribute("mes", map);
            return "/page/detail.jsp";
        } catch (Exception e) {
            log.error("用户登陆异常", e);
        }
        return "/page/login.jsp";
    }

    @RequestMapping("/search")
    public String search(@RequestParam("name") String name, HttpServletRequest request, HttpSession session) {
        if (!session.getAttribute("admin").equals("true")) {
            return "/page/login.jsp";
        } else {
            try {
                List<StudentView> list = studentService.search(infoService.search(name));
                request.setAttribute("info", list);
                request.setAttribute("name", name);
                return "/WEB-INF/admin/browse.jsp";
            } catch (Exception e) {
                log.error("管理员查询异常", e);
            }
            return "/page/login.jsp";
        }
    }
}
