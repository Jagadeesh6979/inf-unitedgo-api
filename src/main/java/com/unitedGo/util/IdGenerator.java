package com.unitedGo.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
@Component
public class IdGenerator {
    private static final String PREFIX = "URS";
    private AtomicInteger counter = new AtomicInteger(0);

    public String generateId() {
        int number = counter.incrementAndGet();
        return PREFIX + String.format("%03d", number);
    }
}
