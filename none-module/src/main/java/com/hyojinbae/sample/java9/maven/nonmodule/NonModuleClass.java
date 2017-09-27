package com.hyojinbae.sample.java9.maven.nonmodule;


import com.hyojinbae.sample.java9.maven.core.Dummy;

import java.util.List;

public class NonModuleClass {
    public static <E> List<E> of(E... e) {
        return Dummy.of(e);
    }
}
