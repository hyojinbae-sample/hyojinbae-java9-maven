package com.hyojinbae.sample.java9.maven.module2;

import com.hyojinbae.sample.java9.maven.module1.ModuleClass1;

import java.util.List;

public class ModuleClass2 {
    public static <E> List<E> of(E... e) {
        return ModuleClass1.of(e);
    }
}
