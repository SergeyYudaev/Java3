package server;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

@Log4j2
public class TestLogLevels {

    Logger log = LogManager.getLogger(TestLogLevels.class);

    @Test
    public void testLogLevels(){

        log.fatal("fatal"); // 100
        log.error("error"); // 200
        log.warn("warn"); // 300
        log.info("info"); // 400
        log.debug("debug"); // 500
        log.trace("trace"); // 600

    }
}
