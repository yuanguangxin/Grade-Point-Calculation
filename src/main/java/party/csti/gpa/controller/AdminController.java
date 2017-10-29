package party.csti.gpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import party.csti.gpa.model.Message;
import party.csti.gpa.service.InfoService;
import party.csti.gpa.service.MessageService;
import party.csti.gpa.service.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private StudentService studentService;
    @Resource
    private InfoService infoService;
    @Resource
    private MessageService messageService;
    @Value("#{config['admin.user']}")
    private String user;
    @Value("#{config['admin.pass']}")
    private String pass;

    @RequestMapping("/login")
    public String login(@RequestParam("user") String username, @RequestParam("pass") String password) {
        try {
            if (username.equals(user) && password.equals(pass)) {
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            log.error("管理员登陆异常", e);
        }
        return "";
    }

    @RequestMapping("/getInfo")
    public String getInfo(HttpServletRequest request, @RequestParam(value = "content", defaultValue = "") String content) {
        try {
            request.setAttribute("info", infoService.queryInfo(content));
        } catch (Exception e) {
            log.error("管理员获取信息异常", e);
            return "";
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("/getPassword")
    public String getPassword(@RequestParam("stuId") String stuId) {
        try {
            return studentService.getPassword(stuId);
        } catch (Exception e) {
            log.error("获取密码异常", e);
            return "error";
        }
    }

    @RequestMapping("/getAllMessage")
    public String getAllMessage(HttpServletRequest request) {
        try {
            List<Message> list = messageService.getAllMessages();
            Collections.reverse(list);
            request.setAttribute("message", list);
        } catch (Exception e) {
            log.error("管理员获取留言异常", e);
            return "";
        }
        return "";
    }

    @RequestMapping("/reply")
    public String reply(@RequestParam("mesId") int mesId, @RequestParam("reply") String reply) {
        try {
            messageService.reply(mesId, reply);
        } catch (Exception e) {
            log.error("回复异常", e);
            return "";
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("/deleteMessgae")
    public String deleteMessage(@RequestParam("mesId") int mesId) {
        try {
            messageService.deleteMessage(mesId);
        }catch (Exception e){
            log.error("删除留言异常",e);
            return "error";
        }
        return "ok;";
    }
}
