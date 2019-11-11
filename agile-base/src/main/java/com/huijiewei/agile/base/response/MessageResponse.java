package com.huijiewei.agile.base.response;

import lombok.Data;

@Data
public class MessageResponse {
    private String message;

    public static MessageResponse of(String message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(message);
        return messageResponse;
    }
}
