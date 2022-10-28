package com.tmax.cloud.superpx.global.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.trace("Trace Level �׽�Ʈ");
        log.debug("DEBUG Level �׽�Ʈ");
        log.info("INFO Level �׽�Ʈ");
        log.warn("Warn Level �׽�Ʈ");
        log.error("ERROR Level �׽�Ʈ");
    }
}