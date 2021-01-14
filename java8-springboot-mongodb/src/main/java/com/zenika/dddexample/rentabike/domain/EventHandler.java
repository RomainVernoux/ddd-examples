package com.zenika.dddexample.rentabike.domain;

public interface EventHandler {

    void handle(Event event);
}
