package com.prototype.chat.service;

import com.prototype.chat.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


/**
 * Created by david.hong on 2/11/2015.
 */
@Service
public class ChatServiceImpl implements ChatService {

    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatServiceImpl(SimpMessageSendingOperations messagingTemplate, SimpMessagingTemplate simpMessagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    //@Scheduled(fixedDelay=2000)
    public void sendMessages(Content content) {
            content.setSubject("Chat Subject 123");
            content.setMessage("Chat Message 123");
            this.simpMessagingTemplate.convertAndSend("/queue/message-" + content.getClientId() , content);
            //this.messagingTemplate.convertAndSendToUser(content.getClientId(),"/queue/message",content);
    }

    @Override
    public void sendMessagesToAll(Content content) {
        content.setSubject("Chat Subject broadcast");
        content.setMessage("Chat Message broadcast");
        this.simpMessagingTemplate.convertAndSend("/topic/broadcast", content);
    }
}
