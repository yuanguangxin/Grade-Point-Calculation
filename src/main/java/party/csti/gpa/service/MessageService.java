package party.csti.gpa.service;

import org.springframework.stereotype.Service;
import party.csti.gpa.dao.MessageMapper;
import party.csti.gpa.model.Message;
import party.csti.gpa.model.MessageExample;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;

    public void leaveMessage(Message message) {
        messageMapper.insert(message);
    }

    public void reply(int id, String replay) {
        Message message = messageMapper.selectByPrimaryKey(id);
        message.setReply(replay);
    }

    public List<Message> getAllMessages() {
        MessageExample messageExample = new MessageExample();
        MessageExample.Criteria criteria = messageExample.createCriteria();
        criteria.andIdIsNotNull();
        return messageMapper.selectByExample(messageExample);
    }

    public void deleteMessage(int id) {
        messageMapper.deleteByPrimaryKey(id);
    }

}
