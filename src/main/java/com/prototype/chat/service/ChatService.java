package com.prototype.chat.service;

import com.prototype.chat.Content;

/**
 * Created by david.hong on 2/11/2015.
 */
public interface ChatService {
    void sendMessages(Content content);
    void sendMessagesToAll(Content content);
}
