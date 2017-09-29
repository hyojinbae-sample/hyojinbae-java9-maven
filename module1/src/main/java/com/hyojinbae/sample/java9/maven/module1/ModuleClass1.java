package com.hyojinbae.sample.java9.maven.module1;

//import com.hyojinbae.sample.java9.maven.core.Dummy;//unnamed moduleに含まれていると、コンパイルできない
import java.util.Arrays;
import java.util.List;

public class ModuleClass1 {
    public static <E> List<E> of(E... e) {
//        return Dummy.of(e);  // Dummy.ofを利用できない
        return List.of(e);
    }
}
