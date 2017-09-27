package com.hyojinbae.sample.java9.maven.core;

import java.util.Arrays;
import java.util.List;

public class Dummy {
    public static <E> List<E> of(E... e) {
        return List.of(e);
    }
}
