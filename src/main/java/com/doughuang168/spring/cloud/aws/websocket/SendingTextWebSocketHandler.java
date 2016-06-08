package com.doughuang168.spring.cloud.aws.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class SendingTextWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.sessions.add(session);
    }

    public <T> void broadcastToSessions(DataWithTimestamp<T> dataToSend) throws IOException {
        String payload = this.jsonMapper.writeValueAsString(dataToSend);
        sendTestMessageToAllSessions(payload);
    }

    private void sendTestMessageToAllSessions(String payload) throws IOException {
        List<WebSocketSession> sessionsToRemove = new ArrayList<>();
        for (WebSocketSession session : this.sessions) {
            if (!session.isOpen()) {
                sessionsToRemove.add(session);
            }
        }
        this.sessions.removeAll(sessionsToRemove);

        for (WebSocketSession session : this.sessions) {
            session.sendMessage(new TextMessage(payload));
        }
    }

}