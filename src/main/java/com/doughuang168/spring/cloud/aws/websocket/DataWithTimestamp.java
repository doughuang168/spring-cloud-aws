package com.doughuang168.spring.cloud.aws.websocket;


import java.util.Date;


public class DataWithTimestamp<T> {

    private final T data;

    private final String timestamp;

    public DataWithTimestamp(T data) {
        this(data, String.valueOf(new Date().getTime()));
    }

    public DataWithTimestamp(T data, String timestamp) {
        this.timestamp = timestamp;
        this.data = data;
    }
}