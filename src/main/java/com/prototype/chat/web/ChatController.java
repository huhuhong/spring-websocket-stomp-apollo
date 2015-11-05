package com.prototype.chat.web;

import com.prototype.chat.Content;
import com.prototype.chat.service.ChatService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.hong on 2/11/2015.
 */
@Controller
public class ChatController {

    private static final Log logger = LogFactory.getLog(ChatController.class);

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/message")
    public void executeMessage(Content content, MessageHeaders messageHeaders) {
        logger.debug("Subject for " + content.getSubject());
        MultiValueMap<String, String> params = (MultiValueMap<String, String>)messageHeaders.get("nativeHeaders");
        logger.debug("Message Header for " + params.get("client-id"));
        String clientId = params.getFirst("client-id");
        content.setClientId(clientId);
        chatService.sendMessages(content);
    }

    @MessageMapping("/broadcast")
    public void executeBoardcast(Content content) {
        chatService.sendMessagesToAll(content);
    }

    @SubscribeMapping("/board")
    public List<Content> GetList()
    {
        //List<Content> contentList = new List<Content>();
        Content msg1 = new Content();
        msg1.setMessage("Message 1");
        msg1.setSubject("Subject 1");

        Content msg2 = new Content();
        msg1.setMessage("Message 2");
        msg1.setSubject("Subject 2");

        ArrayList<Content> contentList = new ArrayList<Content>();

        contentList.add(msg1);
        contentList.add(msg2);

        return contentList ;
    }
}
