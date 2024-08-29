package com.example.cinemaapp.client;

import org.fluentd.logger.FluentLogger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FluentdClient {

    private static final String FLUENTD_HOST = "localhost";
    private static final String FLUENTD_PORT = "5050";
    private static final String FLUENTD_TAG = "cinema_app";

    private final FluentLogger logger = FluentLogger.getLogger(FLUENTD_TAG, FLUENTD_HOST, Integer.parseInt(FLUENTD_PORT));

    public void send(String tag, Map<String, Object> data) {
        logger.log(tag, data);
    }

}
