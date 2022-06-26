package com.student.smartETailor.interfaces;

import com.student.smartETailor.models.Conversation;

import java.util.List;

public interface ConversationsInterface {
    void onConversationsReceived(List<Conversation> conversationList);
    void onError(String error);
}
