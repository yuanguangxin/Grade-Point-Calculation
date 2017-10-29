package party.csti.gpa.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.csti.gpa.model.Message;
import party.csti.gpa.service.MessageService;
import party.csti.gpa.util.Dates;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private MessageService messageService;

    @RequestMapping("/all")
    public String message() {
        try {
            List<Message> list = messageService.getAllMessages();
            Collections.reverse(list);
            if (list.size() < 10) {
                return JSONObject.toJSON(list.subList(0, list.size())).toString();
            } else {
                return JSONObject.toJSON(list.subList(0, 10)).toString();
            }
        } catch (Exception e) {
            log.error("获取留言信息异常", e);
        }
        return "error";
    }

    @RequestMapping("/leave")
    public String leave(Message message) {
        try {
            message.setTimes(Dates.getDate());
            messageService.leaveMessage(message);
        } catch (Exception e) {
            log.error("留言异常", e);
            return "error";
        }
        return "ok";
    }
}
