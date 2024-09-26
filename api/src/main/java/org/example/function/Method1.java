package org.example.function;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
// public class Method1 extends AbstractFunction<Void>{
public class Method1 implements Supplier<Void>{
    
    private static final String PROCESS_NAME = "メソッド１";

    private final String message = "hello world from method1";

    private final Logger logger = LoggerFactory.getLogger(Method1.class);

    @Override
    public Void get() {
        logger.info(message);
        return null;
    }

    // public Method1() {
    //     super(PROCESS_NAME);
    // }

    // @Override
    // protected Void execute() throws Exception {
    //     logger.info(message);
    //     return null;
    // }
}
