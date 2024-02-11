package com.mounanga.accountservice.common.event;

import lombok.Getter;


@Getter
public class BaseEvent<T> {
    private final T id;
    public BaseEvent(T id) {
        this.id = id;
    }
}
