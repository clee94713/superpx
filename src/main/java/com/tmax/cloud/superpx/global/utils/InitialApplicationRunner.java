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
        log.trace("Trace Level 테스트");
        log.debug("DEBUG Level 테스트");
        log.info("INFO Level 테스트");
        log.warn("Warn Level 테스트");
        log.error("ERROR Level 테스트");
    }
}