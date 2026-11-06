package ru.university.clothes.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service 
public class IdGeneratorService {
    private final AtomicLong sequence = new AtomicLong();

    public long next(){
        return sequence.incrementAndGet();
    }
}
