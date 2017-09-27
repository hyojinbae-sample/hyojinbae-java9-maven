package com.hyojinbae.sample.java9.maven.module2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ModuleClass2 {
    public static <E> List<E> of(E... e) {
        Logger logger = LoggerFactory.getLogger("Main");
        logger.info("info: {}", 1);
        logger.warn("warn: {}", 2);
        logger.error("error: {}", 3);
        return null;
    }
}
