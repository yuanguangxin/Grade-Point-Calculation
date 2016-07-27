package com.service;

import com.mapper.MessageMapper;
import com.models.Message;
import com.models.MessageExample;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessageMapper messageMapper;

    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public MessageMapper getMessageMapper() {
        return messageMapper;
    }

    public void addMessage(Message message){
        messageMapper.insert(message);
    }

    public List<Message> getAllMessages(){
        MessageExample messageExample = new MessageExample();
        MessageExample.Criteria criteria = messageExample.createCriteria();
      //  criteria.andIdIsNull();
        List<Message> list = messageMapper.selectByExample(messageExample);
        List<Message> list1 = new ArrayList<>();
//        for (int i=1;i<=5;i++){
//            list1.add(list.get(list.size()-i));
//        }
        return list1;
    }
}
