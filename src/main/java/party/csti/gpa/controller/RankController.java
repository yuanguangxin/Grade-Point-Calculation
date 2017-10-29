package party.csti.gpa.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.csti.gpa.service.InfoService;

import javax.annotation.Resource;

@Controller()
@RequestMapping("/rank")
public class RankController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private InfoService infoService;

    @RequestMapping("/npoint")
    public String npoint() {
        try {
            return JSONObject.toJSON(infoService.getNpointRank()).toString();
        } catch (Exception e) {
            log.error("获取当前学年排名异常", e);
        }
        return "error";
    }

    @RequestMapping("/tpoint")
    public String tpoint() {
        try {
            return JSONObject.toJSON(infoService.getTpointRank()).toString();
        } catch (Exception e) {
            log.error("获取总学年排名异常", e);
        }
        return "error";
    }
}
