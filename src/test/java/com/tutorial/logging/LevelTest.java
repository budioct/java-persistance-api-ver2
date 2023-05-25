package com.tutorial.logging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelTest {

    private static final Logger log = LoggerFactory.getLogger(LevelTest.class);

    @Test
    void testLog(){
        log.trace("trace");
        log.debug("debuge");
        log.info("info");
        log.warn("warning");
        log.error("Error");
    }


}
